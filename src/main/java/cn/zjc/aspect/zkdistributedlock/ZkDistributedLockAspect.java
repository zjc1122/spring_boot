package cn.zjc.aspect.zkdistributedlock;

import cn.zjc.config.ZkConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangjiacheng on 2017/11/12
 */
@Aspect
@Component
public class ZkDistributedLockAspect {
    private static final Logger log = LoggerFactory.getLogger(ZkDistributedLockAspect.class);

    @Autowired
    ZkConfig zkConfig;

    @Pointcut("@annotation(cn.zjc.aspect.zkdistributedlock.ZkDistributedLock) && execution(* cn.zjc..*(..))")
    private void lockPoint(){}

    @Around("lockPoint()")
    public Object zkDistributedLock(ProceedingJoinPoint pjp) throws Throwable {
        //获取注解信息
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        ZkDistributedLock lockInfo = method.getAnnotation(ZkDistributedLock.class);
        Integer expireTime = lockInfo.expireTime();

        CuratorFramework client = zkConfig.getZkClient();
        client.start();
        InterProcessMutex lock = zkConfig.getZkLock();
        Object obj = null;
        try {
            if(lock.acquire(expireTime, TimeUnit.SECONDS)){
                log.info("得到锁...");
                obj = pjp.proceed();
                Thread.sleep(500);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                //判断是否持有锁
                if(lock.isAcquiredInThisProcess()){
                    log.info("释放锁...");
                    lock.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        client.close();
        return obj;
    }
}
