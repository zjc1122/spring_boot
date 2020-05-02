package cn.zjc.rabbitmq.receive.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component

public class DirectReceive {

    @RabbitHandler
    @RabbitListener(queues="directQueue")
    public void receive(String str) {
        System.out.println("客户端接收到消息:"+str);
    }

}