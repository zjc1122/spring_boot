package cn.zjc.aspect.redissonlock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author : zhangjiacheng
 * @ClassName : RedissonLock
 * @date : 2020/6/12
 * @Description :redisson分布式锁注解类
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonLock {
    /**
     * redis的key
     *
     * @return
     */
    String key();

    /**
     * 持锁时间持锁超过此时间自动丢弃锁
     * 单位秒,默认0秒
     * 如果为0表示通过看门狗自动续租，线程不主动释放锁会一直持有锁，需要监控异常锁的情况，避免造成死锁阻塞业务
     */
    long leaseTime() default 0;


    /**
     * 没有获取到锁的情况下且继续等待，睡眠指定秒数继续获取锁，也就是轮询获取锁的时间
     * 默认为3秒
     *
     * @return
     */
    long waitTime() default 3;

    /**
     *
     * @return
     */
    TimeUnit unit() default TimeUnit.SECONDS;

}
