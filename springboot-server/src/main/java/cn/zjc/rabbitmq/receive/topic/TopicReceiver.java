package cn.zjc.rabbitmq.receive.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicReceiver {

    @RabbitListener(queues = "topicQueue")
    @RabbitHandler
    public void process(String msg) {
        System.out.println("客户端1接收到消息  : " +msg);
    }

    @RabbitListener(queues = "topicQueueMore")
    @RabbitHandler
    public void processMore(String msg) {
        System.out.println("客户端2接收到消息  : " +msg);
    }
}