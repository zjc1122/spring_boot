package com.zjc.formework.aop;

import com.zjc.formework.aop.intercept.ZMethodInvocation;
import com.zjc.formework.aop.support.ZAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @ClassName : JdkDynamicAopProxy
 * @Author : zhangjiacheng
 * @Date : 2021/1/15
 * @Description : TODO
 */
public class ZJdkDynamicAopProxy implements ZAopProxy, InvocationHandler {


    private ZAdvisedSupport advised;

    public ZJdkDynamicAopProxy(ZAdvisedSupport config){
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {

        return Proxy.newProxyInstance(classLoader,this.advised.getTargetClass().getInterfaces(),this);

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicMethodMatchers = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, this.advised.getTargetClass());
        Object target = this.advised.getTarget();
        ZMethodInvocation invocation = new ZMethodInvocation(proxy,target,method,args,this.advised.getTargetClass(),interceptorsAndDynamicMethodMatchers);
        return invocation.proceed();
    }
}
