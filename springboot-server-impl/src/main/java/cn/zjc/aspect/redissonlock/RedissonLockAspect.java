package cn.zjc.aspect.redissonlock;

import cn.zjc.aspect.zklock.ZkDistributedLockAspect;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author : zhangjiacheng
 * @ClassName : RedissonLockAspect
 * @date : 2020/6/12
 * @Description : Redisson分布式锁切面类
 */
@Aspect
@Component
public class RedissonLockAspect {
    private static final Logger logger = LoggerFactory.getLogger(ZkDistributedLockAspect.class);

    @Resource
    private RedissonClient redissonClient;

    @Pointcut("@annotation(cn.zjc.aspect.redissonlock.RedissonLock) && execution(* cn.zjc..*(..))")
    private void lockPoint() {
    }

    @Around("lockPoint()")
    public Object redisDistributedLock(ProceedingJoinPoint pjp) throws Throwable {
        //获取Lock注解信息
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        RedissonLock lockInfo = method.getAnnotation(RedissonLock.class);
        String lockKey = lockInfo.key();
        if (StringUtils.isBlank(lockKey)) {
            throw new IllegalArgumentException("配置参数错误,lockKey不能为空！");
        }
        long leaseTime = lockInfo.leaseTime();
        long waitTime = lockInfo.waitTime();
        TimeUnit unit = lockInfo.unit();
        Object obj = null;
        boolean lock = false;
        RLock rLock = redissonClient.getLock(lockKey);
        try {
            //尝试去上锁
            if (leaseTime > 0) {
                //设置过期时间，到期自动释放的锁
                lock = rLock.tryLock(waitTime, leaseTime, unit);
            } else {
                //不设置过期时间，看门狗自动续租的锁
                lock = rLock.tryLock(waitTime, unit);
            }
            if (lock && rLock.isHeldByCurrentThread()) {
                logger.info("当前线程得到锁...");
                obj = pjp.proceed();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            if (lock && rLock.isHeldByCurrentThread()) {
                //当前线程是否持有此锁，持有就删除锁
                logger.info("释放锁...");
                rLock.unlock();

            }
        }
        return obj;
    }


}
