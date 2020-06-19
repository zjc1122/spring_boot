package com.zjc.controller;

import com.zjc.service.IUserServer;
import com.zjc.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<User> findAllUser() {
        log.info("分页查询");
        return userService.findAll();
    }

    /**
     * 新增修改删除方法清除对应的缓存
     * 对于@Cacheable和@CacheEvict适用于批量操作 有查询一条数据的需求时, 修改方法可以增加一个@CachePut方法
     * 目前测试@CachePut方法只能更新一条数据的缓存，不能更新批量缓存
     *
     * @return
     */
//    @RequestMapping(value = "/user/addUser", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ADMIN')")
//    @CacheEvict(value = "users", key = "#root.caches[0].name")
//    public JsonResult addUser() {
//        User user = User
//                .builder()
//                .userId(5)
//                .password("123456")
//                .userName("张三")
//                .phone("13188888888")
//                .build();
//        userService.save(user);
//        logger.info("用户添加用户:{}", user.getUserName());
//        return JsonResult.success(user.getUserName() + "添加成功!");
//    }
}
