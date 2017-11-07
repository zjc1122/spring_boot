package cn.zjc.test;

import cn.zjc.aspect.demo.Demo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangjiacheng on 2017/11/7.
 */
@Controller
public class DemoTest {
    @RequestMapping(value = "/hello")
    @ResponseBody
    @Demo(methodName = "zjctest")
    public String sayHello(){
        return "Hello World!!!666";
    }
}
