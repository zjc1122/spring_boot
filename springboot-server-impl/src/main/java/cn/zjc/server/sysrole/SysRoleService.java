package cn.zjc.server.sysrole;

import cn.zjc.model.sysrole.SysRole;
import cn.zjc.server.base.BaseServer;

import java.util.List;

/**
 * Created by zhangjiacheng on 2018/2/2.
 */
public interface SysRoleService extends BaseServer<SysRole> {

    List<SysRole> findRoleByUserId(Long UserId);
}
