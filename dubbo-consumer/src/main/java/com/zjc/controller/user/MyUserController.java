package com.zjc.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zjc.Entity.user.User;
import com.zjc.Server.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MyUserController {


    @Reference(group = "userGroup",version = "1.0.0")
    private UserService userService;


    @RequestMapping(value = "/dubbo/user/findAll", method = RequestMethod.POST)
    public User findAllUser() {
        User user = userService.selectByPrimaryKey(1001L);
        return user;
    }
}
