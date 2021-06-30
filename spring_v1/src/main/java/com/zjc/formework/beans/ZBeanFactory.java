package com.zjc.formework.beans;

/**
 * @ClassName : ZBeanFactory
 * @Author : zhangjiacheng
 * @Date : 2021/1/6
 * @Description : 单利工厂的顶层设计
 */
public interface ZBeanFactory {

    /**
     * 根据beanName从IOC容器中获得一个实例Bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName);

    Object getBean(Class<?> beanClass);
}
