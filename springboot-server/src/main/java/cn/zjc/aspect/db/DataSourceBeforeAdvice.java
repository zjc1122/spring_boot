package cn.zjc.aspect.db;

import cn.zjc.enums.DataBaseTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author : zhangjiacheng
 * @ClassName : DataSourceBeforeAdvice
 * @date : 2018/6/11
 * @Description : 数据源切面
 */
@Aspect
@Component
@Slf4j
public class DataSourceBeforeAdvice {

    @Pointcut("execution(* cn.zjc.mapper..*(..))||execution(* cn.zjc.server..*(..))")
    public void dataSourceMethodPointCut() {

    }
    @Before("dataSourceMethodPointCut()")
    public void doAround(JoinPoint pjp) {
        //获得注解信息
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        Object target = pjp.getTarget();
        if (log.isInfoEnabled()) {
            String name = target.getClass().getName() + "#" + method.getName();
            log.info("开始执行方法:{}", name);
        }
        //处理数据源,计算路由DB Group
        Annotation trsAnnotation = AnnotationUtils.getAnnotation(method, TargetDataSource.class);
        if (trsAnnotation == null) {
            trsAnnotation = AnnotationUtils.getAnnotation(target.getClass(), TargetDataSource.class);
        }
        if (trsAnnotation != null) {
            DataBaseTypeEnum dataBaseTypeEnum = (DataBaseTypeEnum) AnnotationUtils.getValue(trsAnnotation);
            DataSourceContextHolder.setDbName(dataBaseTypeEnum.get());
            log.info("数据源名称:{}", dataBaseTypeEnum.get());
        } else {
            DataSourceContextHolder.setDbName(null);
        }
//        return pjp.proceed();
    }

    /**
     * 执行完切面后，将线程共享中的数据源名称清空
     *
     * @param pjp
     */
    @After("dataSourceMethodPointCut()")
    public void after(JoinPoint pjp) {
        if (log.isInfoEnabled()) {
            log.info("结束执行方法:{}", pjp.getTarget().getClass().getName() + "#" + ((MethodSignature) pjp.getSignature()).getMethod().getName());
        }
        DataSourceContextHolder.clearDbType();
    }
}
