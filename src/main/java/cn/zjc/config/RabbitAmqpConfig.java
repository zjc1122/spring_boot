package cn.zjc.config;

import com.sun.istack.internal.tools.DefaultAuthenticator;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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
        RabbitAdmin rabbitAdmin =  new RabbitAdmin(cachingConnectionFactory);
        return rabbitAdmin;
    }
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }
    @Bean(name="messages")
    public Queue queueMessages() {
        return new Queue("topic.messages",true);
    }
    /**
     * 将队列topic.messages与exchange绑定，binding_key为topic.#,模糊匹配
     * @param queueMessages
     * @param topicExchange
     */
    @Bean
    Binding bindingExchangeMessages(@Qualifier("messages")Queue queueMessages, TopicExchange topicExchange) {
        Binding binding = BindingBuilder.bind(queueMessages).to(topicExchange).with("topic.#");
        return binding;
    }
}
