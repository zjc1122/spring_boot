package cn.zjc.controller.sysuser;

import cn.zjc.model.sysuser.SysUser;
import cn.zjc.server.sysuser.SysUserService;
import cn.zjc.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public JsonResult login(@AuthenticationPrincipal User sysUser,
                            @RequestParam(value = "logout", required = false) String logout) {
        if (Objects.nonNull(logout)) {
            SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
            return JsonResult.success("退出成功!");
        }
        if (Objects.nonNull(sysUser)) {
            return JsonResult.success("用户:" + sysUser.getUsername() + "登录成功!");
        }
        return JsonResult.failed("登录失败!");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JsonResult register(@RequestParam("username") String username, @RequestParam("password") String password) {
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
