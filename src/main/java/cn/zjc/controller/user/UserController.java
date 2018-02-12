package cn.zjc.controller.user;

import cn.zjc.model.user.User;
import cn.zjc.server.user.UserService;
import cn.zjc.util.JsonResult;
import com.github.pagehelper.PageInfo;
import org.hibernate.annotations.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/findAllUser",method = RequestMethod.POST)
    @Cacheable(value = "users" ,key="'findAllUser'")
    public PageInfo<User> findAllUser(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize){
        User user = new User();
        log.info("===========");
        return userService.selectPageAll(pageNo,pageSize,user);
    }

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public JsonResult addUser(){
        User user = new User();
        log.info("===========");
        user.setUserId(5);
        user.setPassword("springboot");
        user.setPhone("springboot_111");
        user.setUserName("springboot_222");
        userService.save(user);
        return JsonResult.success(user.getUserName(),"添加成功");
    }
}
