package cn.zjc.rabbitmq.sender.topic;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TopicSender {

    @Autowired
    private RabbitTemplate rabbitTemplatenew;

    public void send() {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        String msg = "I am topic.mesaages msg=====";
        System.out.println("发送消息: " + msg);
        System.out.println("callbackSender UUID: " + correlationData.getId());
        rabbitTemplatenew.convertAndSend("topicExchange", "topicQueue", msg,correlationData);
    }

}