package com.zjc.designmode.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName : OrderServiceDynamicProxy
 * @Author : zhangjiacheng
 * @Date : 2020/12/17
 * @Description : TODO
 */
public class OrderServiceDynamicProxy implements InvocationHandler {

    private Object proxyObj;

    public Object getInstance(Object proxyObj){
        this.proxyObj = proxyObj;

        Class<?> clazz = proxyObj.getClass();

        return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before(args[0]);
        Object invoke = method.invoke(proxyObj, args);
        after();
        return invoke;
    }

    private void after() {
        System.out.println("处理后操作");
    }

    private void before(Object obj) {
        try {
            Long getCreatTime = (Long)obj.getClass().getMethod("getCreatTime").invoke(obj);
            System.out.println("处理前操作:" + getCreatTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
