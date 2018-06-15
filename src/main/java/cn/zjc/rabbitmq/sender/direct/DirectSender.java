package cn.zjc.rabbitmq.sender.direct;

import cn.zjc.model.user.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @author : zhangjiacheng
 * @ClassName : DirectSender
 * @date : 2018/6/15
 * @Description : Direct发送
 */
@Component
public class DirectSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send() {
        User user = new User();
        user.setUserName("张三");
        user.setPassword("123");
        String sendMsg = "I am direct.mesaages msg=====" + new Date();
        System.out.println("Direct发送消息:" + sendMsg);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        System.out.println("Direct callbackSender UUID: " + correlationData.getId());
        rabbitTemplate.convertAndSend("directExchange", "directQueue", user, correlationData);
    }
}