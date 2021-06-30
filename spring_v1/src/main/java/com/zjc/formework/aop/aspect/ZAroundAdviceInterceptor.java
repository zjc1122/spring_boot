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
public class ZAroundAdviceInterceptor extends ZAbstractAspectAdvice implements ZAdvice,ZMethodInterceptor {


    private ZJoinPoint joinPoint;

    public ZAroundAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(ZMethodInvocation mi) throws Throwable {
        System.out.println("Around before");
        Object retValue = mi.proceed();
//        mi.getMethod().invoke(mi.getThis(),mi.getArguments());
        this.joinPoint = mi;
        this.aroundValue(retValue,mi.getMethod(),mi.getArguments(),mi.getThis());
        System.out.println("Around after");
        return retValue;
    }

    private void aroundValue(Object retValue, Method method, Object[] arguments, Object aThis) throws Exception {
        super.invokeAdviceMethod(this.joinPoint,retValue,null);
    }

}
