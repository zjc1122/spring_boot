package com.zjc.formework.aop.intercept;

import com.google.common.collect.Maps;
import com.zjc.formework.aop.aspect.ZJoinPoint;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : ZMethodInvocation
 * @Author : zhangjiacheng
 * @Date : 2021/1/15
 * @Description : TODO
 */
public class ZMethodInvocation implements ZJoinPoint {

    private Object proxy;
    private Method method;
    private Object target;
    private Object[] arguments;
    private List<Object> interceptorsAndDynamicMethodMatchers;
    private Class<?> targetClass;
    private Map<String,Object> userAttributes;

    //定义一个索引，从-1开始记录当前拦截器执行的位置
    private int currentInterceptorIndex = -1;

    public ZMethodInvocation(Object proxy, Object target, Method method, Object[] arguments,
            Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers) {

        this.proxy = proxy;
        this.target = target;
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    public Object proceed() throws Throwable {

        //如果Interceptor执行完了，则执行joinPoint
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return this.method.invoke(this.target, this.arguments);
        }

        Object interceptorOrInterceptionAdvice =
                this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
        //如果要动态匹配joinPoint
        if (interceptorOrInterceptionAdvice instanceof ZMethodInterceptor) {
            ZMethodInterceptor mi =
                    (ZMethodInterceptor) interceptorOrInterceptionAdvice;
            return mi.invoke(this);
        } else {
            //动态匹配失败时,略过当前Intercetpor,调用下一个Interceptor
            return proceed();
        }
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public void setUserAttribute(String key, Object value) {
        if (value != null){
            if (this.userAttributes==null){
                this.userAttributes = Maps.newHashMap();
            }
            this.userAttributes.put(key,value);
        }else{
            if (this.userAttributes!=null){
                this.userAttributes.remove(key);
            }
        }
    }

    @Override
    public Object getUserAttribute(String key) {
        return (this.userAttributes != null ? this.userAttributes.get(key) : null);
    }
}
