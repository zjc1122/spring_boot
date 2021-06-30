package com.zjc.formework.aop;

import com.zjc.formework.aop.support.ZAdvisedSupport;

/**
 * @ClassName : CglibAopProxy
 * @Author : zhangjiacheng
 * @Date : 2021/1/15
 * @Description : TODO
 */
public class ZCglibAopProxy implements ZAopProxy{


    private ZAdvisedSupport advised;

    public ZCglibAopProxy(ZAdvisedSupport config){
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
