package cn.zjc.test;

import cn.zjc.aspect.throwing.ThrowingMail;
import cn.zjc.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName : DemoTestController
 * @Author : zhangjiacheng
 * @Date : 2018/6/10
 * @Description : 测试Controller
 */
@Controller
public class DemoTestController {
    @Autowired
    private RedisUtil service;

    @RequestMapping(value = "/hello")
    @ResponseBody
    @ThrowingMail
    public Boolean sayHello() {
        service.expire("ttt", 10);
//        User user = new User();
//        int userId = user.getUserId();
        long ss = 123;
//        Boolean mm = service.setNX("zzzq",1111L);
        Boolean mm = true;
        return mm;
    }
}
