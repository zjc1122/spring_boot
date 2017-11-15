package cn.zjc.server.user;

import cn.zjc.model.User;

import java.util.List;

/**
 * Created by zhangjiacheng on 2017/11/15.
 */
public interface UserService {

    int addUser(User user);

    List<User> findAllUser(int pageNum, int pageSize);
}
