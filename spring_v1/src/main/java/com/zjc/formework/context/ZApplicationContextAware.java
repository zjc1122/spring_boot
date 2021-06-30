package com.zjc.formework.context;

/**
 * @ClassName : ZApplicationContextAware
 * @Author : zhangjiacheng
 * @Date : 2021/1/6
 * @Description : 通过解耦的方式获得IOC容器的顶层设计
 * 后面将通过一个监听器去扫描实现了此接口的类
 * 自动调用setApplicationContext方法，从而将IOC容器注入到目标类中
 */
public interface ZApplicationContextAware {

    void setApplicationContext(ZApplicationContext applicationContext);
}
