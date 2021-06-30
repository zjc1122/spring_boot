package com.zjc.designmode.observer.guava;

import com.google.common.eventbus.Subscribe;

/**
 * @ClassName : GuavaEnvent
 * @Author : zhangjiacheng
 * @Date : 2020/12/24
 * @Description : TODO
 */
public class GuavaEvent {

    @Subscribe
    public void subscribe(String str){
        System.out.println("执行subscribe方法，传入的参数为：" + str);
    }

    @Subscribe
    public void subscribe2(String str){
        System.out.println("执行subscribe2方法，传入的参数为：" + str);
    }
}
