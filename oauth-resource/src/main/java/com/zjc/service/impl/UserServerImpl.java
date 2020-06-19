package com.zjc.service.impl;

import com.zjc.dao.UserDAO;
import com.zjc.service.IUserServer;
import com.zjc.user.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName : IUserServerImpl
 * @Author : zhangjiacheng
 * @Date : 2020/6/17
 * @Description : TODO
 */
@Service
@Primary
public class UserServerImpl implements IUserServer {

    @Resource
    private UserDAO userDAO;

    @Override
    public User findUserById(long id) {
        return userDAO.findUserById(id);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public void save(User user) {
        userDAO.save(user);
    }
}
