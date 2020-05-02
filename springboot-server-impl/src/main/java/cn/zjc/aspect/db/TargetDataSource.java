package cn.zjc.aspect.db;


import cn.zjc.enums.DataBaseTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author : zhangjiacheng
 * @ClassName : TargetDataSource
 * @date : 2018/6/11
 * @Description : 目标数据源注解，注解在方法上指定数据源的名称
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TargetDataSource {
    DataBaseTypeEnum value() default DataBaseTypeEnum.Default_DB;//此处接收的是数据源的名称
}