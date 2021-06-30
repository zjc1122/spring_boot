package com.zjc.formework.aop.intercept;


/**
 * @ClassName : ZMethodInterceptor
 * @Author : zhangjiacheng
 * @Date : 2021/1/15
 * @Description : TODO
 */
public interface ZMethodInterceptor {

    Object invoke(ZMethodInvocation invocation) throws Throwable;
}
