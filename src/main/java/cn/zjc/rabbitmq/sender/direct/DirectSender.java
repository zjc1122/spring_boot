package cn.zjc.rabbitmq.sender.direct;

import cn.zjc.model.user.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.UUID;

@Component
public class DirectSender {

    @Autowired
    private RabbitTemplate rabbitTemplatenew;

    public void send() {
        User user = new User();
        user.setUserName("张三");
        user.setPassword("123");
        String sendMsg = "消息========" + new Date();
        System.out.println("Sender1 : " + sendMsg);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        System.out.println("callbackSender UUID: " + correlationData.getId());
        rabbitTemplatenew.convertAndSend("directExchange", "directQueue",user,correlationData);
    }
}