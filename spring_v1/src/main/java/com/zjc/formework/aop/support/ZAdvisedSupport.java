package com.zjc.formework.aop.support;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zjc.formework.aop.aspect.ZAfterReturningAdviceInterceptor;
import com.zjc.formework.aop.aspect.ZAfterThrowingAdviceInterceptor;
import com.zjc.formework.aop.aspect.ZAroundAdviceInterceptor;
import com.zjc.formework.aop.aspect.ZMethodBeforeAdviceInterceptor;
import com.zjc.formework.aop.config.ZAopConfig;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName : ZAdvisedSupport
 * @Author : zhangjiacheng
 * @Date : 2021/1/15
 * @Description : TODO
 */
public class ZAdvisedSupport {

    private Class<?> targetClass;

    private Object target;

    private ZAopConfig config;

    private Pattern pointCutClassPattern;

    private Map<Method,List<Object>> methodCache;

    public ZAdvisedSupport(ZAopConfig config) {
        this.config = config;
    }

    public Class<?> getTargetClass(){
        return this.targetClass;
    }
    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        parse();
    }

    private void parse() {
        String pointCut = config.getPointCut()
                .replaceAll("\\.","\\\\.")
                .replaceAll("\\\\.\\*",".*")
                .replaceAll("\\(","\\\\(")
                .replaceAll("\\)","\\\\)");

        String pointCutForClassRegex = pointCut.substring(0,pointCut.lastIndexOf("\\(") - 4);

        pointCutClassPattern = Pattern.compile("class " + pointCutForClassRegex.substring(
                pointCutForClassRegex.lastIndexOf(" ") + 1));
        try {

            methodCache = new HashMap<>();

            Pattern pattern = Pattern.compile(pointCut);

            Class<?> aspectClass = Class.forName(this.config.getAspectClass());

            Map<String, Method> aspectMethods = Maps.newHashMap();

            for (Method m : aspectClass.getMethods()) {
                aspectMethods.put(m.getName(),m);
            }

            for (Method m : this.targetClass.getMethods()) {
                String methodString = m.toString();
                if (methodString.contains("throws")) {
                    methodString = methodString.substring(0, methodString.lastIndexOf("throws")).trim();
                }
                Matcher matcher = pattern.matcher(methodString);
                if (matcher.matches()){

                    List<Object> advices = Lists.newLinkedList();
                    //把每一个方法包装成MethodInterceptor
                    //before
                    if (!(null == config.getAspectBefore() || "".equals(config.getAspectBefore()))){
                        //创建一个Advivce
                        advices.add(new ZMethodBeforeAdviceInterceptor(aspectMethods.get(config.getAspectBefore()),aspectClass.newInstance()));
                    }
                    //after
                    if (!(null == config.getAspectAfter() || "".equals(config.getAspectAfter()))){
                        advices.add(new ZAfterReturningAdviceInterceptor(aspectMethods.get(config.getAspectAfter()),aspectClass.newInstance()));
                    }
                    //afterThrowing
                    if (!(null == config.getAspectAfterThrow() || "".equals(config.getAspectAfterThrow()))){
                        ZAfterThrowingAdviceInterceptor throwingAdvice = new ZAfterThrowingAdviceInterceptor(aspectMethods.get(config.getAspectAfterThrow()), aspectClass.newInstance());
                        throwingAdvice.setThrowName(config.getAspectAfterThrowingName());
                        advices.add(throwingAdvice);
                    }
                    //around
                    if (!(null == config.getAspectAround() || "".equals(config.getAspectAround()))){
                        //创建一个Advivce
                        advices.add(new ZAroundAdviceInterceptor(aspectMethods.get(config.getAspectAround()),aspectClass.newInstance()));
                    }
                    methodCache.put(m,advices);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Object getTarget() {
        return this.target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws Exception {
        List<Object> cached = methodCache.get(method);
        if (cached == null){
            Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());
            cached = methodCache.get(m);
            //对代理方法做一个兼容处理
            this.methodCache.put(m,cached);
        }
        return cached;
    }

    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }
}
