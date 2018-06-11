package cn.zjc.aspect.db;

import cn.zjc.enums.DataBaseType;
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
 * @ClassName : DataSourceBeforeAdvice
 * @author : zhangjiacheng
 * @date : 2018/6/11
 * @Description : 数据源切面
 */
@Aspect
@Component
public class DataSourceBeforeAdvice {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceBeforeAdvice.class);

    @Pointcut("execution(* cn.zjc.mapper..*(..))||execution(* cn.zjc.server..*(..))")
    public void dataSourceMethodPointCut() throws Throwable {

    }

    @Before("dataSourceMethodPointCut()")
    public void doAround(JoinPoint pjp) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        Object target = pjp.getTarget();
        String name = "";
        if (logger.isInfoEnabled()) {
            name = target.getClass().getName() + "#" + method.getName();
        }

        //计算路由DB Group
        //处理数据源
        Annotation trsAnnotation = AnnotationUtils.getAnnotation(method, TargetDataSource.class);
        if (trsAnnotation == null) {
            trsAnnotation = AnnotationUtils.getAnnotation(target.getClass(), TargetDataSource.class);
        }
        if (trsAnnotation != null) {
            DataBaseType dataBaseType = (DataBaseType) AnnotationUtils.getValue(trsAnnotation);
            DataSourceContextHolder.setDbName(dataBaseType.get());
        } else {
            DataSourceContextHolder.setDbName(null);
        }
//        return pjp.proceed();
    }

    //执行完切面后，将线程共享中的数据源名称清空
    @After("dataSourceMethodPointCut()")
    public void after(JoinPoint joinPoint) {
        DataSourceContextHolder.clearDbType();
    }
}
