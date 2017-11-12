package cn.zjc.aspect.zkdistributedlock;

import cn.zjc.config.ZkConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjiacheng on 2017/11/12
 */
@Aspect
@Component
public class ZkDistributedLockAspect {

    @Autowired
    ZkConfig zkConfig;
    private static final Logger log = LoggerFactory.getLogger(ZkDistributedLockAspect.class);

    @Pointcut("@annotation(cn.zjc.aspect.zkdistributedlock.ZkDistributedLock) && execution(* cn.zjc..*(..))")
    private void lockPoint(){}

    @Around("lockPoint()")
    public Object zkDistributedLock(ProceedingJoinPoint pjp) throws Throwable {

        CuratorFramework client = zkConfig.getZkClient();
        client.start();
        InterProcessMutex lock = zkConfig.getZkLock();
        Object obj = null;
        try {
            lock.acquire();
            log.info("得到锁...");
            obj = pjp.proceed();
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                log.info("释放锁...");
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        client.close();
        return obj;
    }
}
