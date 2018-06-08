package cn.zjc.controller.user;

import cn.zjc.model.user.User;
import cn.zjc.server.user.UserService;
import cn.zjc.util.JsonResult;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhangjiacheng on 2017/11/16.
 */
@RestController
@RequestMapping(value = "/user")
@PreAuthorize("hasRole('USER')")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/findAllUser", method = RequestMethod.POST)
    @Cacheable(value = "users", key = "'findAllUser'")
    public PageInfo<User> findAllUser(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = new User();
        logger.info("===========");
        return userService.selectPageAll(pageNo, pageSize, user);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public JsonResult addUser() {
        logger.info("===========");
        User user = User
                .builder()
                .userId(5)
                .password("springboot")
                .userName("springboot_222")
                .phone("123445")
                .build();
        userService.save(user);
        return JsonResult.success(user.getUserName() + "添加成功!");
    }
}
