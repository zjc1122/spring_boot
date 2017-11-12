package cn.zjc.aspect.redisdistributedlock;

import cn.zjc.rediscache.RedisUtil;
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
import java.util.concurrent.TimeoutException;

/**
 * Created by zhangjiacheng on 2017/11/7.
 */
@Aspect
@Component
public class RedisLockAspect  {

    @Autowired
    private RedisUtil redisUtil;
    private static final Logger log = LoggerFactory.getLogger(RedisLockAspect.class);

    @Pointcut("@annotation(cn.zjc.aspect.redisdistributedlock.RedisLock) && execution(* cn.zjc..*(..))")
    private void lockPoint(){}

    @Around("lockPoint()")
    public Object arround(ProceedingJoinPoint pjp) throws Throwable {
        //获取RedisLock注解信息
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        RedisLock lockInfo = method.getAnnotation(RedisLock.class);
        String lockKey = lockInfo .value();
        if (lockKey == null ||"".equals(lockKey)) {
            throw new IllegalArgumentException("配置参数错误,lockKey不能为空！");
        }
        // 持有锁超时时间换算为秒
        Integer lockExpire;
        //没有设置超时时间，给一个默认值60秒
        if(lockInfo.keepMills() <= 0){
            lockExpire = 30;
        }else{
            lockExpire = (int)(lockInfo.keepMills() / 1000);
        }
        boolean lock = false;
        Object obj = null;
        try {
            // 获取锁的最大超时时间
            Long maxSleepMills = System.currentTimeMillis() + lockInfo.maxSleepMills();
            while (!lock) {
                //持锁时间
                Long keepMills = System.currentTimeMillis() + lockInfo.keepMills();
                lock = redisUtil.setNX(lockKey, keepMills);
                // 得到锁，没有人加过相同的锁
                if (lock) {
                    //设置失效时间
                    redisUtil.expire(lockKey,lockExpire);
                    log.info("得到锁...");
                    obj = pjp.proceed();
                }
                // 已过期，并且getAndSet后旧的时间戳依然是过期的，可以认为获取到了锁
                else if (System.currentTimeMillis() > redisUtil.get(lockKey) &&
                        (System.currentTimeMillis() > redisUtil.getAndSet(lockKey, keepMills))) {
                    lock = true;
                    log.info("得到锁...");
                    obj = pjp.proceed();
                }
                // 没有得到任何锁
                else {
                    // 继续等待获取锁
                    if (lockInfo.action().equals(RedisLock.LockFailAction.CONTINUE)) {
                        // 如果超过最大等待时间抛出异常
                        log.info("稍后重新请求锁...");
                        if (lockInfo.maxSleepMills() > 0 && System.currentTimeMillis() > maxSleepMills) {
                            throw new TimeoutException("获取锁资源等待超时");
                        }
                        TimeUnit.MILLISECONDS.sleep(lockInfo.sleepMills());
                    }
                    // 放弃等待
                    else {
                        log.info("放弃锁...");
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
                if (System.currentTimeMillis() < (System.currentTimeMillis() + lockInfo.keepMills())){
                    log.info("释放锁...");
                    redisUtil.delete(lockKey);
                }

            }
        }
        return obj;
    }


}
