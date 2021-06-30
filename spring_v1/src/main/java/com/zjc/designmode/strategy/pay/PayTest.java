package com.zjc.designmode.strategy.pay;

/**
 * @ClassName : PayTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/21
 * @Description : TODO
 */
public class PayTest {

    public static void main(String[] args) {
        Order order = new Order("1","202012210001",4000);
        MsgResult pay = order.pay(PayStrategy.ALI_PAY);
        System.out.println(pay);
    }
}
