package cn.zjc.rabbitmq.sender.topic;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
public class TopicSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send() {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        String msg = "I am topic.mesaages msg=====";
        System.out.println("发送消息: " + msg);
        System.out.println("callbackSender UUID: " + correlationData.getId());
        rabbitTemplate.convertAndSend("topicExchange", "topicQueue", msg, correlationData);
    }

}