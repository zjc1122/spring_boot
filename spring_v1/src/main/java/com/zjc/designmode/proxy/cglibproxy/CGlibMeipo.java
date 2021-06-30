package com.zjc.designmode.proxy.cglibproxy;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @ClassName : CGlibMeipo
 * @Author : zhangjiacheng
 * @Date : 2020/12/17
 * @Description : TODO
 */
public class CGlibMeipo implements MethodInterceptor {

    public Object getInstance(Class<?> clazz) throws Exception{
        //相当于proxy，代理工具类
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        before();
        Object obj = proxy.invokeSuper(o, args);
        after();
        return obj;
    }

    private void after() {
        System.out.println("ok的话，准备办事");
    }

    private void before() {
        System.out.println("我是媒婆，我要给你介绍对象，现在已经确认你的需求");
        System.out.println("开始物色");
    }
}
