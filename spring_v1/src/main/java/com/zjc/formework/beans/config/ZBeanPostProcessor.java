package com.zjc.formework.beans.config;


/**
 * @ClassName : ZBeanPostProcessor
 * @Author : zhangjiacheng
 * @Date : 2021/1/13
 * @Description : TODO
 */
public class ZBeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }
}
