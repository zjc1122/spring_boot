package cn.zjc.test;

import cn.zjc.aspect.demo.Demo;
import cn.zjc.rediscache.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangjiacheng on 2017/11/7.
 */
@Controller
public class DemoTestController {
    @Autowired
    private RedisUtil service;

    @RequestMapping(value = "/hello")
    @ResponseBody
    @Demo(methodName = "zjctest")
    public Boolean sayHello(){
       service.expire("ttt",10);
        long ss = 123;
        Boolean mm = service.setNX("zzzq",1111L);
        return mm;
    }
}
