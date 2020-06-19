package cn.zjc.aspect.demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by zhangjiacheng on 2017/11/7.
 */
@Aspect
@Component
public class DemoAspect {

    @Pointcut("@annotation(cn.zjc.aspect.demo.Demo) && execution(* cn.zjc..*(..))" )
    private void demoPointCut() {
    }
    @Around("demoPointCut()")
    private Object demoTest(ProceedingJoinPoint pjp) throws Throwable {
        //得到注解方法Start
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        Demo demo = method.getAnnotation(Demo.class);
        //得到注解方法End
        System.out.println("注解传入的参数methodName()为==========="+demo.methodName());
        System.out.println("###### Aspect Start======");
        Object obj = pjp.proceed();
        System.out.println("######" + obj.toString() + "===========");
        System.out.println("###### Aspect End======");
        return obj;
    }
}
