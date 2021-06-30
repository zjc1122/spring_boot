package com.zjc.designmode.decorator.v2;

/**
 * @ClassName : BattercakeDecotator
 * @Author : zhangjiacheng
 * @Date : 2020/12/23
 * @Description : TODO
 */
public abstract class BattercakeDecorator extends Battercake {

    private Battercake battercake;

    public BattercakeDecorator(Battercake battercake) {
        this.battercake = battercake;
    }

    protected abstract void doSomething();

    @Override
    protected String getMsg() {
        return this.battercake.getMsg();
    }

    @Override
    protected int getPrice() {
        return this.battercake.getPrice();
    }
}
