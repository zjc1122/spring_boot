package com.zjc.formework.aop.aspect;

import java.lang.reflect.Method;

/**
 * @ClassName : ZJoinPoint
 * @Author : zhangjiacheng
 * @Date : 2021/1/17
 * @Description : TODO
 */
public interface ZJoinPoint {

    Object getThis();

    Object[] getArguments();

    Method getMethod();

    void setUserAttribute(String key, Object value);

    Object getUserAttribute(String key);
}
