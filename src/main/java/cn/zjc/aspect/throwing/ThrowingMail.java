package cn.zjc.aspect.throwing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName : ThrowingMail
 * @Author : zhangjiacheng
 * @Date : 2018/6/10
 * @Description : 异常邮件注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ThrowingMail {
}
