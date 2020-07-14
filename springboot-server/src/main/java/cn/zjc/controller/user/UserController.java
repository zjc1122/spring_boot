package cn.zjc.controller.user;


import cn.zjc.util.JsonResult;
import com.github.pagehelper.PageInfo;
import com.zjc.Entity.user.User;
import com.zjc.Server.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : zhangjiacheng
 * @ClassName : UserController
 * @date : 2018/6/13
 * @Description : User权限登录以及配置缓存(示例)
 */
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String[] ROLES = {"USER","ADMIN"};
    @Resource
    private UserService userService;

    /**
     * 使用Spring的@Cacheable做缓存,缓存名字为users,缓存的key为当前被调用的方法使用的Cache(同value值)
     * 缓存应加在service层，这里为了测试放在了Controller
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/user/findAllUser", method = RequestMethod.POST)
    @Cacheable(value = "users", key = "#root.caches[0].name")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public PageInfo<User> findAllUser(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        logger.info("分页查询");
        return userService.selectPageAll(pageNo, pageSize, new User());
    }

    /**
     * 新增修改删除方法清除对应的缓存
     * 对于@Cacheable和@CacheEvict适用于批量操作 有查询一条数据的需求时, 修改方法可以增加一个@CachePut方法
     * 目前测试@CachePut方法只能更新一条数据的缓存，不能更新批量缓存
     *
     * @return
     */
    @RequestMapping(value = "/user/addUser", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "users", key = "#root.caches[0].name")
    public JsonResult addUser() {
        User user = User
                .builder()
                .userId(5)
                .password("123456")
                .userName("张三")
                .phone("13188888888")
                .build();
        userService.save(user);
        logger.info("用户添加用户:{}", user.getUserName());
        return JsonResult.success(user.getUserName() + "添加成功!");
    }
}
