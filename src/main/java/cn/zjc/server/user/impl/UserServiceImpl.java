package cn.zjc.server.user.impl;

import cn.zjc.mapper.user.UserMapper;
import cn.zjc.model.user.User;
import cn.zjc.server.base.impl.BaseServerImpl;
import cn.zjc.server.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangjiacheng on 2017/11/15.
 */
@Service
public class UserServiceImpl  extends BaseServerImpl<User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    public void setBaseMapper(){
        super.setBaseMapper(userMapper);
    }

}
