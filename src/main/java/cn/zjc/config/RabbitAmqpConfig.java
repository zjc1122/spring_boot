package cn.zjc.config;

import cn.zjc.enums.SysUtilCode;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


/**
 * Created by zhangjiacheng on 2017/12/1.
 */
@Configuration
public class RabbitAmqpConfig {
    @Value("${rabbitmq.host}")
    private String host;
    @Value("${rabbitmq.port}")
    private Integer port;
    @Value("${rabbitmq.username}")
    private String user;
    @Value("${rabbitmq.password}")
    private String password;
    @Value("${rabbitmq.virtualHost}")
    private String virtualHost;
    private static final Logger loggere = LoggerFactory.getLogger(RabbitAmqpConfig.class);

    /**
     * 配置链接信息
     *
     * @return
     */
    @Bean
    public ConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setChannelCacheSize(1024);
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
        connectionFactory.setChannelCacheSize(180 * 1000);
        connectionFactory.setConnectionCacheSize(1024);
        connectionFactory.setUsername(user);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherReturns(true);
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory cachingConnectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(cachingConnectionFactory);
        return rabbitAdmin;
    }

    /**
     * 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplatenew() {
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory());
        //数据转换为json存入消息队列
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        //发布确认
        template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            //消息发送到queue时就执行
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    loggere.info("消息成功消费");
                } else {
                    loggere.info("消息发送失败: {},重新发送", cause );
                    throw new RuntimeException(SysUtilCode.MESSAGE_SEND_ERROR.getDesc() + cause);
                }
            }
        });
        return template;
    }

    /**
     * 配置消息交换机
     * 针对消费者配置
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange", true, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange", true, false);
    }

    /**
     * 配置direct消息队列
     * 针对消费者配置
     *
     * @return
     */
    @Bean(name = "directQueue")
    public Queue directQueue() {
        return new Queue("directQueue", true);
    }

    /**
     * 配置topic消息队列
     * 针对消费者配置
     *
     * @return
     */
    @Bean(name = "topicQueue")
    public Queue topicQueue() {
        return new Queue("topicQueue", true);
    }

    @Bean(name = "topicQueueMore")
    public Queue topicQueueMore() {
        return new Queue("topicQueueMore", true);
    }

    /**
     * 将direct消息队列与交换机绑定
     * 针对消费者配置
     *
     * @return
     */
    @Bean
    public Binding directBinding(@Qualifier("directQueue") Queue directQueue) {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(directQueue.getName());
    }

    /**
     * 将topic消息队列与交换机绑定
     * 针对消费者配置
     * 关键字是topicQueue
     */
    @Bean
    public Binding topicBinding(@Qualifier("topicQueue") Queue topicQueue) {
        return BindingBuilder.bind(topicQueue()).to(topicExchange()).with(topicQueue.getName());
    }

    /**
     * 将topic消息队列与交换机绑定
     * 针对消费者配置
     * 关键字是topicQueue.#
     * *表示一个词,#表示零个或多个词
     */
    @Bean
    public Binding topicBindings(@Qualifier("topicQueueMore") Queue topicQueue) {
        return BindingBuilder.bind(topicQueueMore()).to(topicExchange()).with("topicQueue.#");
    }

    @Bean
    public SimpleMessageListenerContainer directListenerContainer(@Qualifier("directQueue") Queue directQueue) {
        return messageContainer(directQueue);
    }

    @Bean
    public SimpleMessageListenerContainer topicListenerContainer(@Qualifier("topicQueue") Queue topicQueue) {
        return messageContainer(topicQueue);
    }

    @Bean
    public SimpleMessageListenerContainer topicMoreListenerContainer(@Qualifier("topicQueueMore") Queue topicQueue) {
        return messageContainer(topicQueue);
    }

    /**
     * 接受消息的监听，这个监听会接受消息队列的消息
     * 针对消费者配置
     *
     * @return
     */
    public SimpleMessageListenerContainer messageContainer(Queue queue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cachingConnectionFactory());
        container.setQueues(queue);
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        //设置确认模式手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] body = message.getBody();
                loggere.info("收到:{},队列的消息:{}", queue.getName() ,new String(body));
                //确认消息成功消费
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        });
        return container;
    }
}
