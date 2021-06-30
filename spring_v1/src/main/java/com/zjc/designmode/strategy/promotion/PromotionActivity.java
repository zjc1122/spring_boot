package com.zjc.designmode.strategy.promotion;

/**
 * @Author : zhangjiacheng
 * @ClassName : ModelConfig
 * @Date : 2019/12/23
 * @Description : TODO
 */
public class PromotionActivity {

    PromotionStrategy promotionStrategy;

    public PromotionActivity(PromotionStrategy promotionStrategy){
        this.promotionStrategy = promotionStrategy;
    }
    public void execute(){
        this.promotionStrategy.doPromotion();
    }
}
