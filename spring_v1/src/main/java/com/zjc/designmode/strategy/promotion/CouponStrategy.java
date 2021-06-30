package com.zjc.designmode.strategy.promotion;

/**
 * @ClassName : CouponStrategy
 * @Author : zhangjiacheng
 * @Date : 2020/12/19
 * @Description : TODO
 */
public class CouponStrategy implements PromotionStrategy{

    @Override
    public void doPromotion() {
        System.out.println("领取优惠券，直降500元");
    }
}
