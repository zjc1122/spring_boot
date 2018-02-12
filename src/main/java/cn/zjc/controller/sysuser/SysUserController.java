package cn.zjc.controller.sysuser;

import cn.zjc.model.sysUser.SysUser;
import cn.zjc.server.sysUser.SysUserService;
import cn.zjc.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by zhangjiacheng on 2018/2/2.
 */
@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public JsonResult login(@AuthenticationPrincipal SysUser model) {
        if (Objects.nonNull(model)) {
            return JsonResult.success("登录成功");
        }
        return JsonResult.success("登录失败");
    }


    @RequestMapping(value = "/register" , method = RequestMethod.POST)
    public JsonResult register(@RequestParam("username") String username, @RequestParam("password") String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(username);
        sysUser.setUsername(username);
        sysUser.setPassword(passwordEncoder.encode(password));
        sysUser.setSalt(UUID.randomUUID().toString());
        sysUserService.save(sysUser);
        return JsonResult.success(sysUser.getUsername(),"注册成功");
    }
}
