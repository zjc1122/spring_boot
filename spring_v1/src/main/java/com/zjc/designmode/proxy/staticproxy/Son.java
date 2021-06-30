package com.zjc.designmode.proxy.staticproxy;

/**
 * @ClassName : Eat
 * @Author : zhangjiacheng
 * @Date : 2020/12/17
 * @Description : TODO
 */
public class Son implements Person{


    @Override
    public void finLove() {
        System.out.println("要求：女的，活的即可");
    }
}
