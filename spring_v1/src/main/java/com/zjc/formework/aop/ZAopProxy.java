package com.zjc.formework.aop;


/**
 * @ClassName : ZAopProxy
 * @Author : zhangjiacheng
 * @Date : 2021/1/15
 * @Description : TODO
 */
public interface ZAopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
