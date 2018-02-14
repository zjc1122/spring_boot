package cn.zjc.server;

import cn.zjc.model.sysUser.SysUser;
import cn.zjc.server.sysUser.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SysUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //查询账号是否存在，是就返回一个UserDetails的对象，否就抛出异常！
        SysUser user;
        try {
            user = sysUserService.getByUsername(userName);
        }catch (Exception e){
            throw new BadCredentialsException("查询用户报错!");
        }
        if (user == null) {
            throw new BadCredentialsException(userName + "这个用户不存在!");
        }
        return user;
    }
}
