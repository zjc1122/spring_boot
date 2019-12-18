package cn.zjc.server.sysuser;

import cn.zjc.model.sysuser.SysUser;
import cn.zjc.server.base.BaseServer;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangjiacheng on 2018/2/2.
 */
public interface SysUserService extends BaseServer<SysUser> {

    /**
     * 根据名称查询用户
     *
     * @param username
     * @return
     */
    SysUser getByUsername(String username);

    String getUserToken(String username, String password) throws Exception;
}
