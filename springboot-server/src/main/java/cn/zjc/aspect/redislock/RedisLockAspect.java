package cn.zjc.aspect.redislock;

import cn.zjc.aspect.zklock.ZkDistributedLockAspect;
import cn.zjc.server.util.JedisService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author : zhangjiacheng
 * @ClassName : RedisLockAspect
 * @date : 2018/6/11
 * @Description : redis分布式锁切面类
 */
@Aspect
@Component
public class RedisLockAspect {
    private static final Logger logger = LoggerFactory.getLogger(ZkDistributedLockAspect.class);
    @Resource
    private JedisService jedisService;

    @Pointcut("@annotation(cn.zjc.aspect.redislock.RedisLock) && execution(* cn.zjc..*(..))")
    private void lockPoint() {
    }

    @Around("lockPoint()")
    public Object redisDistributedLock(ProceedingJoinPoint pjp) throws Throwable {
        //获取RedisLock注解信息
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        RedisLock lockInfo = method.getAnnotation(RedisLock.class);
        String lockKey = lockInfo.value();
        if (StringUtils.isBlank(lockKey)) {
            throw new IllegalArgumentException("配置参数错误,lockKey不能为空！");
        }
        boolean lock = false;
        Object obj = null;
        try {
            // 获取锁的最大超时时间
            long maxSleepMills = System.currentTimeMillis() + lockInfo.maxSleepMills();
            while (!lock) {
                //持锁时间
                String keepMills = String.valueOf(System.currentTimeMillis() + lockInfo.keepMills());
                //上锁
                lock = jedisService.setNX(lockKey, keepMills, lockInfo.keepMills());
                // 得到锁，没有人加过相同的锁
                if (lock) {
                    logger.info("得到锁...");
                    obj = pjp.proceed();
                }
                // 已过期，并且getAndSet后旧的时间戳依然是过期的，可以认为获取到了锁
                else if (System.currentTimeMillis() > jedisService.get(lockKey) &&
                        (System.currentTimeMillis() > jedisService.getAndSet(lockKey, keepMills))) {
                    lock = true;
                    logger.info("得到锁...");
                    obj = pjp.proceed();
                }
                // 没有得到任何锁
                else {
                    // 继续等待获取锁
                    if (lockInfo.action().equals(RedisLock.LockFailAction.CONTINUE)) {
                        // 如果超过最大等待时间抛出异常
                        logger.info("稍后重新请求锁...");
                        if (lockInfo.maxSleepMills() > 0 && System.currentTimeMillis() > maxSleepMills) {
                            throw new TimeoutException("获取锁资源等待超时");
                        }
                        TimeUnit.MILLISECONDS.sleep(lockInfo.sleepMills());
                    } else {
                        // 放弃等待
                        logger.info("放弃锁...");
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            // 如果获取到了锁，释放锁
            if (lock) {
                //锁没有过期就删除key
                if (System.currentTimeMillis() < (System.currentTimeMillis() + lockInfo.keepMills())) {
                    logger.info("释放锁...");
                    jedisService.delete(lockKey);
                }

            }
        }
        return obj;
    }


}
