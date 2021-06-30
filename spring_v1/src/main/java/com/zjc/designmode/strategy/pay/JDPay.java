package com.zjc.designmode.strategy.pay;

/**
 * @ClassName : AliPay
 * @Author : zhangjiacheng
 * @Date : 2020/12/19
 * @Description : TODO
 */
public class JDPay extends Payment {

    @Override
    public String getName() {
        return "京东支付";
    }

    @Override
    protected double queryBalance(String uid) {
        return 500;
    }
}
