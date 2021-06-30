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
public class ZMethodBeforeAdviceInterceptor extends ZAbstractAspectAdvice implements ZAdvice,ZMethodInterceptor {


    private ZJoinPoint joinPoint;

    public ZMethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    private void before(Method method, Object[] args, Object target) throws Exception{
        //传入给织入的参数
        super.invokeAdviceMethod(this.joinPoint,null,null);
    }

    @Override
    public Object invoke(ZMethodInvocation mi) throws Throwable {
        this.joinPoint = mi;
        before(mi.getMethod(),mi.getArguments(),mi.getThis());
        return mi.proceed();
    }
}
