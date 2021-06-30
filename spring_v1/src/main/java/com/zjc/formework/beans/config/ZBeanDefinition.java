package com.zjc.formework.beans.config;

import lombok.Data;

/**
 * @ClassName : ZBeanDefinition
 * @Author : zhangjiacheng
 * @Date : 2021/1/6
 * @Description : TODO
 */
@Data
public class ZBeanDefinition {

    private String beanClassName;

    private boolean lazyInit = false;

    private String factoryBeanName;

    private boolean isSingleton = true;
}
