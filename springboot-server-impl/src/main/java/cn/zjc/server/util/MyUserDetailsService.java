package cn.zjc.server.util;

import cn.zjc.enums.SystemCodeEnum;
import cn.zjc.model.sysrole.SysRole;
import cn.zjc.model.sysuser.SysUser;
import cn.zjc.server.sysrole.SysRoleService;
import cn.zjc.server.sysuser.SysUserService;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author : zhangjiacheng
 * @ClassName : MyUserDetailsService
 * @date : 2018/6/14
 * @Description : SpringSecurity用户登录校验类
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //查询账号是否存在，是就返回一个UserDetails的对象，否就抛出异常！
        SysUser user;
        try {
            user = sysUserService.getByUsername(userName);
        } catch (Exception e) {
            throw new BadCredentialsException(SystemCodeEnum.USER_ERROR.getDesc());
        }
        if (user == null) {
            throw new BadCredentialsException(userName + SystemCodeEnum.USER_NOT_EXIST.getDesc());
        }
        //查询用户的权限
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        List<SysRole> roles = sysRoleService.findRoleByUserId(user.getId());
        if (Objects.nonNull(roles)) {
            for (SysRole sysRole : roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(sysRole.getName()));
            }
        }
        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
