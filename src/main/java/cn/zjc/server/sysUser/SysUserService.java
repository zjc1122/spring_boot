package cn.zjc.server.sysUser;

import cn.zjc.model.sysUser.SysUser;
import cn.zjc.server.base.BaseServer;

/**
 * Created by zhangjiacheng on 2018/2/2.
 */
public interface SysUserService extends BaseServer<SysUser> {

    SysUser getByUsername(String username);
}
