package com.zjc.designmode.strategy.pay;

/**
 * @ClassName : AliPay
 * @Author : zhangjiacheng
 * @Date : 2020/12/19
 * @Description : TODO
 */
public class UnionPay extends Payment {

    @Override
    public String getName() {
        return "银联支付";
    }

    @Override
    protected double queryBalance(String uid) {
        return 700;
    }
}
