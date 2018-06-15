package cn.zjc.test;

import cn.zjc.rabbitmq.sender.direct.DirectSender;
import cn.zjc.rabbitmq.sender.topic.TopicSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : zhangjiacheng
 * @ClassName : SendTestController
 * @date : 2018/6/15
 * @Description : rabbitMQ测试Controller
 */
@RestController
@RequestMapping(value = "/test")
public class SendTestController {
    @Resource
    private DirectSender directSender;
    @Resource
    private TopicSender topicSender;

    /**
     * 向消息队列中发送消息
     *
     * @param msg
     * @return
     */
    @RequestMapping("/directSend")
    public String directSend(String msg) {
        directSender.send();
        return null;
    }

    @RequestMapping("/topicSend")
    public String topicSend(String msg) {
        topicSender.send();
        return null;
    }
}