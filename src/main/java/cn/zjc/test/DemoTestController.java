package cn.zjc.test;

import cn.zjc.aspect.throwing.ThrowingMail;
import cn.zjc.model.user.User;
import cn.zjc.server.user.UserService;
import cn.zjc.util.RedisUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName : DemoTestController
 * @Author : zhangjiacheng
 * @Date : 2018/6/10
 * @Description : 测试Controller
 */
@RestController
@RequestMapping(value = "/test")
public class DemoTestController {

    private static final Logger logger = LoggerFactory.getLogger(DemoTestController.class);
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserService userService;


    @ResponseBody
    @ThrowingMail
    @RequestMapping(value = "/hello")
    public Boolean sayHello() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Boolean mm = redisUtil.setNX("zzzq",1111L);
        redisUtil.expire("ttt", 10);
        //测试异常邮件
        User user = new User();
        int userId = user.getUserId();
        long ss = 123;
        Boolean mm = true;
        return mm;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @Cacheable(value = "users", key = "#root.caches[0].name")
    public PageInfo<User> findAllUser(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        logger.info("分页查询");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.selectPageAll(pageNo, pageSize, new User());
    }
}
