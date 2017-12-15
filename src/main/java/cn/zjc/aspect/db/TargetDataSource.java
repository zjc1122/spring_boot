package cn.zjc.aspect.db;


import cn.zjc.enums.DataBaseType;
import java.lang.annotation.*;


/**
 * 目标数据源注解，注解在方法上指定数据源的名称
 * Created by zhangjiacheng on 2017/12/13.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TargetDataSource {
    DataBaseType value() default DataBaseType.Default_DB;//此处接收的是数据源的名称
}