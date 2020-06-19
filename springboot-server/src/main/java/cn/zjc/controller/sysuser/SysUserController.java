package cn.zjc.controller.sysuser;

import cn.zjc.enums.SystemCodeEnum;
import cn.zjc.model.sysuser.SysUser;
import cn.zjc.server.sysuser.SysUserService;
import cn.zjc.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 注册接口
     *
     * @param username
     * @param password
     * @return
     */
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

    /**
     * 注销成功返回结果
     * @return
     */
    @RequestMapping(value = "/logout/success", method = RequestMethod.GET)
    public JsonResult logoutSuccess() {
        return JsonResult.success(SystemCodeEnum.LOGOUT_SUCCESS.getName());
    }

}
