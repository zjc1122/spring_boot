package com.zjc.designmode.decorator.v2;

import com.github.pagehelper.PageHelper;

/**
 * @ClassName : BattercakeTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/23
 * @Description : TODO
 */
public class BattercakeTest {

    public static void main(String[] args) {

        Battercake battercake;
        //普通煎饼
        battercake = new BaseBattercake();
        //加一个鸡蛋
        battercake = new EggDecorator(battercake);
        //在加一个鸡蛋
        battercake = new EggDecorator(battercake);
        //加一个香肠
        battercake = new SausageDecorator(battercake);
        System.out.println(battercake.getMsg() + ",总价格" + battercake.getPrice());
        PageHelper.offsetPage(5,10);
    }
}
