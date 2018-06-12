package cn.zjc.server.sysuser.impl;

import cn.zjc.mapper.sysuser.SysUserMapper;
import cn.zjc.model.sysuser.SysUser;
import cn.zjc.server.base.impl.BaseServerImpl;
import cn.zjc.server.sysuser.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjiacheng on 2018/2/2.
 */
@Service
@Transactional(rollbackFor = Exception.class)
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
