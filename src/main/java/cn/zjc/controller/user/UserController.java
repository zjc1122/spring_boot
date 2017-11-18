package cn.zjc.controller.user;

import cn.zjc.model.user.User;
import cn.zjc.server.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all")
    @ResponseBody
    public Object findAllUser(){
        User user = new User();
        System.out.println("===========");
        log.info("===========");
        return userService.selectPageAll(2,2,user);
    }
    @RequestMapping(value = "/add")
    @ResponseBody
    public String addUser(){
        User user = new User();
        log.info("===========");
        user.setUserId(5);
        user.setPassword("5");
        user.setPhone("5");
        user.setUserName("5");
        userService.save(user);
        return "success";
    }
}
