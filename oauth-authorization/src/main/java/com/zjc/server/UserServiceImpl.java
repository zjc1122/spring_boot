package com.zjc.server;

import com.google.common.collect.Lists;
import com.zjc.domain.SysRole;
import com.zjc.domain.SysUser;
import com.zjc.mapper.RoleMapper;
import com.zjc.mapper.UserMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author : zhangjiacheng
 * @date : 2018/6/14
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;

//    @Override
//    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        return userMapper.findByName(name);
//    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //查询账号是否存在，是就返回一个UserDetails的对象，否就抛出异常！
        SysUser user;
        try {
            user = userMapper.findByName(userName);
        } catch (Exception e) {
            throw new BadCredentialsException("用户查询出错");
        }
        if (user == null) {
            throw new BadCredentialsException("用户不存在");
        }
        //查询用户的权限
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        List<SysRole> roles = roleMapper.findByUid(user.getId());
        if (Objects.nonNull(roles)) {
            for (SysRole sysRole : roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(sysRole.getName()));
            }
        }
        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
