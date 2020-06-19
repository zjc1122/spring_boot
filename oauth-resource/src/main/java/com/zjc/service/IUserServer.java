package com.zjc.service;

import com.zjc.user.User;

import java.util.List;

/**
 * @ClassName : IUserServer
 * @Author : zhangjiacheng
 * @Date : 2020/6/17
 * @Description : TODO
 */
public interface IUserServer {

    User findUserById(long id);

    List<User> findAll();

    void save(User user);
}
