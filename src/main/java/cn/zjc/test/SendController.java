package cn.zjc.test;

import cn.zjc.rabbitmq.sender.HelloSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SendController {
    @Autowired
    private HelloSender helloSender;

    /**
     * 向消息队列1中发送消息
     * @param msg
     * @return
     */
    @RequestMapping("send1")
    public String send1(String msg){
        helloSender.send();
        return null;
    }
}