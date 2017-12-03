package cn.zjc.rabbitmq.receive;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues="queue_one")
public class HelloReceive {

    @RabbitHandler
    public void processC(String str) {
        System.out.println("Receive:"+str);
    }

}