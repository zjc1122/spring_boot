package com.zjc.mebatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @ClassName : ZMapperProxy
 * @Author : zhangjiacheng
 * @Date : 2021/1/31
 * @Description : TODO
 */
public class ZMapperProxy implements InvocationHandler {

    private ZSqlSession sqlSession;

    public ZMapperProxy(ZSqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String statementId = method.getDeclaringClass().getName() + "." + method.getName();
        return sqlSession.selectOne(statementId,args[0]);
    }
}
