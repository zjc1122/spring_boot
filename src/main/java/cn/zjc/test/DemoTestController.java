package cn.zjc.test;

import cn.zjc.aspect.throwing.ThrowingMail;
import cn.zjc.model.user.User;
import cn.zjc.util.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName : DemoTestController
 * @Author : zhangjiacheng
 * @Date : 2018/6/10
 * @Description : 测试Controller
 */
@Controller
public class DemoTestController {

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping(value = "/hello")
    @ResponseBody
    @ThrowingMail
    public Boolean sayHello() {
        redisUtil.expire("ttt", 10);
        //测试异常邮件
        User user = new User();
        int userId = user.getUserId();
        long ss = 123;
//        Boolean mm = redisUtil.setNX("zzzq",1111L);
        Boolean mm = true;
        return mm;
    }
}
