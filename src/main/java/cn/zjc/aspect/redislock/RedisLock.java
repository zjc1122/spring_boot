package cn.zjc.aspect.redislock;

import java.lang.annotation.*;

/**
 * Created by zhangjiacheng on 2017/11/7.
 * 同步锁：
 * 主要作用是在服务器集群环境下保证方法的synchronize；
 * 标记在方法上，使该方法的执行具有互斥性，并不保证并发执行方法的先后顺序；
 * 如果原有“A任务”获取锁后任务执行时间超过最大允许持锁时间，且锁被“B任务”获取到，
 * 在“B任务”成功获取锁会并不会终止“A任务”的执行；
 * 注意：
 * 使用过程中需要注意keepMills、action、sleepMills、maxSleepMills等参数的场景使用；
 * 需要安装redis，并使用spring和spring-data-redis等，借助redis NX等方法实现。
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {
    /**
     * redis的key
     * @return
     */
    String value();
    /**
     * 持锁时间、超时时间，持锁超过此时间自动丢弃锁
     * 单位毫秒,默认30秒
     * 如果为0表示永远不释放锁，在设置为0的情况下CONTINUE是没有意义的
     * 但是没有比较强的业务要求下，不建议设置为0
     */
    long keepMills() default 30 * 1000;
    /**
     * 当获取锁失败时候动作
     */
    LockFailAction action() default LockFailAction.CONTINUE;

    enum LockFailAction{
        /**
         * 放弃
         */
        GIVEUP,
        /**
         * 继续
         */
        CONTINUE;
    }
    /**
     * 没有获取到锁的情况下且继续等待，睡眠指定毫秒数继续获取锁，也就是轮询获取锁的时间
     * 默认为1000毫秒
     *
     * @return
     */
    long sleepMills() default 1000;
    /**
     * 锁获取超时时间：
     * 没有获取到锁的情况下且继续等待，最大等待时间，如果超时抛出
     * {"java.util.concurrent.TimeoutException.TimeoutException"}
     * 可捕获此异常做相应业务处理；
     * 单位毫秒,默认一分钟，如果设置为0即为没有超时时间，一直获取下去；
     *
     * @return
     */
    long maxSleepMills() default 60 * 1000;
}
