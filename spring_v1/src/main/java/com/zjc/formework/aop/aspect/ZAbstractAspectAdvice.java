package com.zjc.formework.aop.aspect;

import java.lang.reflect.Method;

/**
 * @ClassName : ZAbstractAspectAdvice
 * @Author : zhangjiacheng
 * @Date : 2021/1/16
 * @Description : TODO
 */
public abstract class ZAbstractAspectAdvice implements ZAdvice{

    private Method aspectMethod;

    private Object aspectTarget;

    public ZAbstractAspectAdvice(Method aspectMethod, Object aspectTarget){
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    protected Object invokeAdviceMethod(ZJoinPoint joinPoint, Object returnValue, Throwable tx) throws Exception{

        Class<?>[] paramTypes = this.aspectMethod.getParameterTypes();

        if (null==paramTypes || paramTypes.length==0){
            return this.aspectMethod.invoke(aspectTarget);
        }else{
            Object[] args = new Object[paramTypes.length];

            for (int i = 0; i < paramTypes.length; i++) {
                if (paramTypes[i] == ZJoinPoint.class){
                    args[i] = joinPoint;
                }else if (paramTypes[i] == Throwable.class){
                    args[i] = tx;
                }else if (paramTypes[i] == Object.class){
                    args[i] = returnValue;
                }
            }

            return this.aspectMethod.invoke(aspectTarget,args);
        }
    }

}
