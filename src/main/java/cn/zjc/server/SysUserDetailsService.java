package cn.zjc.server;

import cn.zjc.model.sysrole.SysRole;
import cn.zjc.model.sysuser.SysUser;
import cn.zjc.server.sysrole.SysRoleService;
import cn.zjc.server.sysuser.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SysUserDetailsService implements UserDetailsService {

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
        }catch (Exception e){
            throw new BadCredentialsException("查询用户报错!");
        }
        if (user != null) {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            List<SysRole> roles = sysRoleService.findRoleByUserId(Long.valueOf(user.getId()));
            if(Objects.nonNull(roles)){
                for (SysRole sysRole:roles) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysRole.getName());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }else {
            throw new BadCredentialsException(userName + "这个用户不存在!");
        }
    }
}
