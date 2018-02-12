package cn.zjc.server.sysUser.impl;

import cn.zjc.mapper.sysuser.SysUserMapper;
import cn.zjc.model.sysUser.SysUser;
import cn.zjc.server.base.impl.BaseServerImpl;
import cn.zjc.server.sysUser.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangjiacheng on 2018/2/2.
 */
@Service
public class SysUserServiceImpl extends BaseServerImpl<SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    public void setBaseMapper(){
        super.setBaseMapper(sysUserMapper);
    }

    @Override
    public SysUser getByUsername(String username) {

        return sysUserMapper.selectSysUserByUsername(username);
    }
}
