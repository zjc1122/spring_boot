package com.zjc.formework.aop.aspect;

import com.zjc.formework.aop.intercept.ZMethodInterceptor;
import com.zjc.formework.aop.intercept.ZMethodInvocation;

import java.lang.reflect.Method;

/**
 * @ClassName : ZMethodBeforeAdvice
 * @Author : zhangjiacheng
 * @Date : 2021/1/16
 * @Description : TODO
 */
public class ZAfterReturningAdviceInterceptor extends ZAbstractAspectAdvice implements ZAdvice,ZMethodInterceptor {


    private ZJoinPoint joinPoint;

    public ZAfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(ZMethodInvocation mi) throws Throwable {
        Object retValue = mi.proceed();
        this.joinPoint = mi;
        this.afterReturningValue(retValue,mi.getMethod(),mi.getArguments(),mi.getThis());
        return retValue;
    }

    private void afterReturningValue(Object retValue, Method method, Object[] arguments, Object aThis) throws Exception {
        super.invokeAdviceMethod(this.joinPoint,retValue,null);
    }
}
