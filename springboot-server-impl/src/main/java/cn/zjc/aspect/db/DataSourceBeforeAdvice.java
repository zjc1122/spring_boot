package cn.zjc.aspect.db;

import cn.zjc.enums.DataBaseTypeEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class DataSourceBeforeAdvice {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceBeforeAdvice.class);

    @Pointcut("execution(* cn.zjc.mapper..*(..))||execution(* cn.zjc.server..*(..))")
    public void dataSourceMethodPointCut() {

    }
    @Before("dataSourceMethodPointCut()")
    public void doAround(JoinPoint pjp) {
        //获得注解信息
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        Object target = pjp.getTarget();
        if (logger.isInfoEnabled()) {
            String name = target.getClass().getName() + "#" + method.getName();
            logger.info("开始执行方法:{}", name);
        }
        //处理数据源,计算路由DB Group
        Annotation trsAnnotation = AnnotationUtils.getAnnotation(method, TargetDataSource.class);
        if (trsAnnotation == null) {
            trsAnnotation = AnnotationUtils.getAnnotation(target.getClass(), TargetDataSource.class);
        }
        if (trsAnnotation != null) {
            DataBaseTypeEnum dataBaseTypeEnum = (DataBaseTypeEnum) AnnotationUtils.getValue(trsAnnotation);
            DataSourceContextHolder.setDbName(dataBaseTypeEnum.get());
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
        if (logger.isInfoEnabled()) {
            logger.info("结束执行方法:{}", pjp.getTarget().getClass().getName() + "#" + ((MethodSignature) pjp.getSignature()).getMethod().getName());
        }
        DataSourceContextHolder.clearDbType();
    }
}
