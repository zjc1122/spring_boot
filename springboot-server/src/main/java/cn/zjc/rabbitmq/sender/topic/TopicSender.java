package cn.zjc.rabbitmq.sender.topic;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @author : zhangjiacheng
 * @ClassName : TopicSender
 * @date : 2018/6/15
 * @Description : Topic发送
 */
@Component
public class TopicSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send() {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        String msg = "I am topic.mesaages msg=====" + new Date();
        System.out.println("Topic发送消息: " + msg);
        System.out.println("Topic callbackSender UUID: " + correlationData.getId());
        rabbitTemplate.convertAndSend("topicExchange", "topicQueue", msg, correlationData);
    }

}