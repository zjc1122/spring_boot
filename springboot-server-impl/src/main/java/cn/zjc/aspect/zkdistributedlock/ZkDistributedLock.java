package cn.zjc.aspect.zkdistributedlock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : zhangjiacheng
 * @ClassName : ZkDistributedLock
 * @date : 2018/6/11
 * @Description : zk分布式锁注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZkDistributedLock {
    /**
     * 设置锁的超时时间
     *
     * @return
     */
    int expireTime() default 20;
}
