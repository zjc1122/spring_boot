package com.zjc.dao;

import com.zjc.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangjiacheng
 * @date 2020/6/17 23:32
 */
@Repository
public interface UserDAO extends JpaRepository<User,Long> {

    User findUserById(long id);
}