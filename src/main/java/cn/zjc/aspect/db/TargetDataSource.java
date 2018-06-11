package cn.zjc.aspect.db;


import cn.zjc.enums.DataBaseType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @ClassName : TargetDataSource
 * @author : zhangjiacheng
 * @date : 2018/6/11
 * @Description : 目标数据源注解，注解在方法上指定数据源的名称
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TargetDataSource {
    DataBaseType value() default DataBaseType.Default_DB;//此处接收的是数据源的名称
}