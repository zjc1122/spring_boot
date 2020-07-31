package com.zjc.controller;

import com.google.gson.GsonBuilder;
import com.zjc.service.IUserServer;
import com.zjc.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;

/**
 * @author : zhangjiacheng
 * @ClassName : UserController
 * @date : 2018/6/13
 */
@RestController
@Slf4j
public class UserController {

    private static final String[] ROLES = {"USER","ADMIN"};
    @Resource
    private IUserServer userService;

    /**
     *
     * @return
     */
    @RequestMapping(value = "/user/findAll", method = RequestMethod.POST)
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public List<User> findAllUser(@RequestParam("key") String key) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(new GsonBuilder().serializeNulls().create().toJson(authentication.getAuthorities()));
        log.info("key------>{}",key);
        log.info("获取oauth访问权限");
        return userService.findAll();
    }

    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
