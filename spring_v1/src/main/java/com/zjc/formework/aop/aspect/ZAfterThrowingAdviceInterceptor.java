package com.zjc.formework.aop.aspect;

import com.zjc.formework.aop.intercept.ZMethodInterceptor;
import com.zjc.formework.aop.intercept.ZMethodInvocation;

import java.lang.reflect.Method;

/**
 * @ClassName : ZAfterThrowingAdviceInterceptor
 * @Author : zhangjiacheng
 * @Date : 2021/1/16
 * @Description : TODO
 */
public class ZAfterThrowingAdviceInterceptor extends ZAbstractAspectAdvice implements ZAdvice,ZMethodInterceptor {


    private String throwName;

    public ZAfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(ZMethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        }catch (Throwable e){
            invokeAdviceMethod(mi,null,e.getCause());
            throw e;
        }
    }

    public void setThrowName(String throwName){
        this.throwName = throwName;
    }
}
