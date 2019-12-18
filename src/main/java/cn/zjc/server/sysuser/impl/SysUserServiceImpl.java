package cn.zjc.server.sysuser.impl;

import cn.zjc.mapper.sysuser.SysUserMapper;
import cn.zjc.model.sysuser.SysUser;
import cn.zjc.server.base.impl.BaseServerImpl;
import cn.zjc.server.sysuser.SysUserService;
import cn.zjc.util.JwtTokenUtil;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by zhangjiacheng on 2018/2/2.
 */
@Service
public class SysUserServiceImpl extends BaseServerImpl<SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    public void setBaseMapper() {
        super.setBaseMapper(sysUserMapper);
    }
    @Autowired
    private AuthenticationManager authenticationManager;
    @Resource
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public SysUser getByUsername(String username) {
        return sysUserMapper.selectSysUserByUsername(username);
    }

    @Override
    public String getUserToken(String username, String password){
        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(userToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }
}
