package com.zjc.designmode.decorator.v1;

/**
 * @ClassName : BattercakeWithEgg
 * @Author : zhangjiacheng
 * @Date : 2020/12/23
 * @Description : TODO
 */
public class BattercakeWithEgg extends Battercake {

    @Override
    protected String getMsg() {
        return super.getMsg() + "加一个鸡蛋";
    }

    @Override
    protected int price() {
        return super.price() + 1;
    }
}
