package com.zjc.formework.beans.support;

import com.zjc.formework.beans.config.ZBeanDefinition;
import com.zjc.formework.context.support.ZAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName : ZDefaultListableBeanFactory
 * @Author : zhangjiacheng
 * @Date : 2021/1/6
 * @Description : TODO
 */
public class ZDefaultListableBeanFactory extends ZAbstractApplicationContext {

    //存储注册信息的BeanDefinition,伪IOC容器
    protected final Map<String, ZBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
}
