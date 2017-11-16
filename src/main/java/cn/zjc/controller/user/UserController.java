package cn.zjc.controller.user;

import cn.zjc.model.User;
import cn.zjc.server.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangjiacheng on 2017/11/16.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all")
    @ResponseBody
    public Object findAllUser(){

        System.out.println("===========");

        return userService.findAllUser(1,1);
    }
    @RequestMapping(value = "/add")
    @ResponseBody
    public String addUser(){
        User user = new User();
        System.out.println("===========");
        user.setUserId(4);
        user.setPassword("4");
        user.setPhone("4");
        user.setUserName("4");
        userService.addUser(user);
        return "success";
    }
}
