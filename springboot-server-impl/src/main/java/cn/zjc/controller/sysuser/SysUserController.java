package cn.zjc.controller.sysuser;

import cn.zjc.model.sysuser.SysUser;
import cn.zjc.server.sysuser.SysUserService;
import cn.zjc.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author : zhangjiacheng
 * @ClassName : SysUserController
 * @date : 2018/6/15
 * @Description : 系统用户Controller
 */
@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public JsonResult login(@AuthenticationPrincipal User sysUser,
//                            @RequestParam(value = "logout", required = false) String logout) {
//        if (Objects.nonNull(logout)) {
//            SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
//            return JsonResult.success("退出成功!");
//        }
//        if (Objects.nonNull(sysUser)) {
//            return JsonResult.success("用户:" + sysUser.getUserName() + "登录成功!");
//        }
//        return JsonResult.failed("登录失败!");
//    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResult login(String username, String password, HttpServletResponse response) {
        try {
//            String token = sysUserService.getUserToken(username, password);
            System.out.println(1111);
//            response.addHeader("Authorization", "Bearer " + token);
//            response.setContentType(JsonResult.JSON_CONTENT_TYPE);
//            response.setCharacterEncoding("UTF-8");
        } catch (Exception e) {
            return JsonResult.failed(e.getMessage());
        }
        return JsonResult.success(null);
    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public JsonResult login(String username, String password, HttpServletResponse response) {
//        try {
//            String token = sysUserService.getUserToken(username, password);
//            response.addHeader("Authorization", "Bearer " + token);
//            response.setContentType(JsonResult.JSON_CONTENT_TYPE);
//            response.setCharacterEncoding("UTF-8");
//        } catch (Exception e) {
//            return JsonResult.failed(e.getMessage());
//        }
//        return JsonResult.success(null);
//    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public JsonResult logout() {
        System.out.println(1111111);
        return JsonResult.success(null);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JsonResult register(@RequestParam("username") String username, @RequestParam("password") String password) {
        SysUser user = sysUserService.getByUsername(username);
        if (user != null) {
            return JsonResult.failed("用户名已存在");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(username);
        sysUser.setUsername(username);
        sysUser.setPassword(passwordEncoder.encode(password));
        sysUser.setSalt(UUID.randomUUID().toString());
        sysUserService.save(sysUser);
        return JsonResult.success("用户名:" + sysUser.getUsername() + "注册成功!");
    }
}
