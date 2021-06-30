package com.zjc.designmode.decorator.v1;

/**
 * @ClassName : BattercakeTese
 * @Author : zhangjiacheng
 * @Date : 2020/12/23
 * @Description : TODO
 */
public class BattercakeTese {

    public static void main(String[] args) {
        Battercake battercake = new Battercake();
        System.out.println(battercake.getMsg() + ",总价格" + battercake.price());

        BattercakeWithEgg egg = new BattercakeWithEgg();
        System.out.println(egg.getMsg() + ",总价格" + egg.price());
    }
}
