package cn.zjc.controller.user;

import cn.zjc.model.user.User;
import cn.zjc.server.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhangjiacheng on 2017/11/16.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all")
    public Object findAllUser(){
        User user = new User();
        log.info("===========");
        return userService.selectPageAll(1,15,user);
    }

    @RequestMapping(value = "/add")
    public String addUser(){
        User user = new User();
        log.info("===========");
        user.setUserId(5);
        user.setPassword("springboot");
        user.setPhone("springboot_111");
        user.setUserName("springboot_222");
        userService.save(user);
        return "success";
    }
}
