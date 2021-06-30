package com.zjc.designmode.decorator.v2;

/**
 * @ClassName : BaseBattercake
 * @Author : zhangjiacheng
 * @Date : 2020/12/23
 * @Description : TODO
 */
public class BaseBattercake extends Battercake{

    @Override
    protected String getMsg(){
        return "煎饼";
    }

    @Override
    protected int getPrice() {
        return 5;
    }

}
