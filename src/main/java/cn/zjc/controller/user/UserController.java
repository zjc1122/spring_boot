package cn.zjc.controller.user;

import cn.zjc.model.user.User;
import cn.zjc.server.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        User user = new User();
        System.out.println("===========");

        return userService.selectPageAll(2,2,user);
    }
    @RequestMapping(value = "/add")
    @ResponseBody
    public String addUser(){
        User user = new User();
        System.out.println("===========");
        user.setUserId(5);
        user.setPassword("5");
        user.setPhone("5");
        user.setUserName("5");
        userService.save(user);
        return "success";
    }
}
