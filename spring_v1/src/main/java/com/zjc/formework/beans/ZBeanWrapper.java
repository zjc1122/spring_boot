package com.zjc.formework.beans;

/**
 * @ClassName : ZBeanWrapper
 * @Author : zhangjiacheng
 * @Date : 2021/1/8
 * @Description : TODO
 */
public class ZBeanWrapper {

    private Object wrappedInstance;
    private Class<?> wrappedClass;

    public ZBeanWrapper(Object wrappedInstance){
        this.wrappedInstance = wrappedInstance;
    }
    public Object getWrappedInstance(){
        return this.wrappedInstance;
    }


    public Class<?> getWrappedClass(){
        return this.wrappedInstance.getClass();
    }
}
