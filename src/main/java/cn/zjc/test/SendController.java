package cn.zjc.test;

import cn.zjc.rabbitmq.sender.direct.DirectSender;
import cn.zjc.rabbitmq.sender.topic.TopicSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SendController {
    @Autowired
    private DirectSender directSender;
    @Autowired
    private TopicSender topicSender;
    /**
     * 向消息队列中发送消息
     * @param msg
     * @return
     */
    @RequestMapping("directSend")
    public String directSend(String msg)throws Exception{
        directSender.send();
        return null;
    }
    @RequestMapping("topicSend")
    public String topicSend(String msg){
        topicSender.send();
        return null;
    }
}