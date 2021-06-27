package cn.zjc.test;

import cn.zjc.aspect.throwing.ThrowingMail;
import cn.zjc.server.util.RedisService;
import cn.zjc.server.util.RedissonService;
import cn.zjc.util.JsonResult;
import com.github.pagehelper.PageInfo;
import com.zjc.Entity.user.User;
import com.zjc.Server.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : DemoTestController
 * @Author : zhangjiacheng
 * @Date : 2018/6/10
 * @Description : 测试Controller
 */
@RestController
@RequestMapping(value = "/test")
@Slf4j
public class DemoTestController {

    @Resource
    private RedisService redisService;
    @Resource
    private UserService userService;
    @Resource
    private RedissonService redissonService;


    @ResponseBody
    @ThrowingMail
    @RequestMapping(value = "/hello")
    public Boolean sayHello(String code) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Boolean mm = redisUtil.setNX("zzzq",1111L);
        redisService.expire("ttt", 10);
        //测试异常邮件
//        User user = new User();
//        int userId = user.getUserId();
//        long ss = 123;
        System.out.println(code);
        Boolean mm = true;
        return mm;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @Cacheable(value = "users", key = "#root.caches[0].name")
    public PageInfo<User> findAllUser(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        log.info("分页查询");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.selectPageAll(pageNo, pageSize, new User());
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.POST)
    public JsonResult findUser(@PathVariable("id") Long id) {
        System.out.println(id);
        log.info("查询");
        return JsonResult.success(userService.selectByPrimaryKey(id));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResult update(User user) {
        userService.update(user);
        return JsonResult.success(null);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(Long id) {
        userService.deleteById(id);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public JsonResult list() {
        return JsonResult.success(null);
    }

    @RequestMapping(value = "/lock")
    @ResponseBody
    public void test(String recordId) {
        RLock lock = redissonService.getRLock(recordId);
        try {
            boolean bs = lock.tryLock(5, 6, TimeUnit.SECONDS);
            if (bs) {
                // 业务代码
                log.info("进入业务代码: " + recordId);

                lock.unlock();
            } else {
                Thread.sleep(300);
            }
        } catch (Exception e) {
            log.error("", e);
            lock.unlock();
        }
    }

}
