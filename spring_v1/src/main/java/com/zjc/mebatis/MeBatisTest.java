package com.zjc.mebatis;

/**
 * @ClassName : MebatisTest
 * @Author : zhangjiacheng
 * @Date : 2021/1/30
 * @Description : TODO
 */
public class MeBatisTest {
    public static void main(String[] args) {
        ZSqlSession sqlSession = new ZSqlSession(new ZConfiguration(),new ZExecutor());
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectById(1006);
        System.out.println(user);
    }
}
