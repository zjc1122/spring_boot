package cn.zjc.server.user.user.impl;

import cn.zjc.mapper.user.UserMapper;
import cn.zjc.model.User;
import cn.zjc.server.user.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangjiacheng on 2017/11/15.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        return userMapper.selectAllUser();
    }
}
