package com.zjc.designmode.strategy.promotion;


/**
 * @ClassName : StrategyTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/10
 * @Description : TODO
 */
public class StrategyTest {

    public static void main(String[] args) {
//        PromotionActivity activity1 = new PromotionActivity(new CouponStrategy());
//        PromotionActivity activity2 = new PromotionActivity(new CashBackStrategy());
//        activity1.execute();
//        activity2.execute();
        String promotionKey = "group";
        PromotionActivity activity = new PromotionActivity(PromotionStrategyFactory.getPromotionStrategy(promotionKey));
        activity.execute();
    }
}
