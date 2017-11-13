package cn.zjc.aspect.zkdistributedlock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangjiacheng on 2017/11/12
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZkDistributedLock {
    /**
     * 设置锁的超时时间
     * @return
     */
    int expireTime() default 20;
}
