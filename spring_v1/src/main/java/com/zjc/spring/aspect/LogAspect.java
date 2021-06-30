package com.zjc.spring.aspect;

import com.zjc.formework.aop.aspect.ZJoinPoint;

import java.util.Arrays;

/**
 * @ClassName : LogAspect
 * @Author : zhangjiacheng
 * @Date : 2021/1/16
 * @Description : TODO
 */
public class LogAspect {

    private long startTime = System.currentTimeMillis();

    public void before(ZJoinPoint joinPoint){
        //记录调用的开始时间
        joinPoint.setUserAttribute("startTime_"+joinPoint.getMethod().getName(),startTime);
        System.out.println("invoke method before" + "\nTargetObject:" + joinPoint.getThis()
                + "\nArgs:" + Arrays.toString(joinPoint.getArguments()));
    }

    public void after(ZJoinPoint joinPoint){
        //得到调用方法的时间
        System.out.println("invoke method after" + "\nTargetObject:" + joinPoint.getThis()
                + "\nArgs:" + Arrays.toString(joinPoint.getArguments()));
        Long startTime = (Long) joinPoint.getUserAttribute("startTime_" + joinPoint.getMethod().getName());
        System.out.println("use time:" + (System.currentTimeMillis() - startTime));
    }

    public void afterThrowing(ZJoinPoint joinPoint,Throwable tx){
        //异常检测，获得异常信息

        System.out.println("throwing" + "\nTargetObject:" + joinPoint.getThis()
                            + "\nArgs:" + Arrays.toString(joinPoint.getArguments())
                            + "\nThrows:" + tx.getMessage());
    }

    public void around(ZJoinPoint joinPoint){
        System.out.println("invoke around method");
    }

}
