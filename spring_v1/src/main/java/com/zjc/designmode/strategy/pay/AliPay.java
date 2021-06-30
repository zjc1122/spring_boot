package com.zjc.designmode.strategy.pay;

/**
 * @ClassName : AliPay
 * @Author : zhangjiacheng
 * @Date : 2020/12/19
 * @Description : TODO
 */
public class AliPay extends Payment {

    @Override
    public String getName() {
        return "支付宝";
    }

    @Override
    protected double queryBalance(String uid) {
        return 900;
    }
}
