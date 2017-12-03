package cn.zjc.rabbitmq.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.UUID;

@Component
public class HelloSender {
    @Autowired
    private AmqpTemplate template;
    @Autowired
    private RabbitTemplate rabbitTemplatenew;
    public void send() {
        String sendMsg = "发送消息========" + new Date();
        System.out.println("Sender1 : " + sendMsg);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        System.out.println("callbackSender UUID: " + correlationData.getId());
        rabbitTemplatenew.convertAndSend("directExchange", "queue_one_key1",sendMsg,correlationData);
    }
}