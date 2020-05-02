package cn.zjc.server.sysrole.impl;

import cn.zjc.mapper.sysrole.SysRoleMapper;
import cn.zjc.model.sysrole.SysRole;
import cn.zjc.server.base.impl.BaseServerImpl;
import cn.zjc.server.sysrole.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangjiacheng on 2018/2/2.
 */
@Service
public class SysRoleServiceImpl extends BaseServerImpl<SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    public void setBaseMapper() {
        super.setBaseMapper(sysRoleMapper);
    }

    @Override
    public List<SysRole> findRoleByUserId(Long UserId) {
        return sysRoleMapper.findRoleByUserId(UserId);
    }
}
