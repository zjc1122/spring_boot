package com.zjc.designmode.strategy.promotion;

/**
 * @Author : zhangjiacheng
 * @ClassName : ModelService
 * @Date : 2019/12/21
 * @Description : 策略设计模式
 */
public interface PromotionStrategy {

    void doPromotion();

    default String printClassName(){
        return this.getClass().getName();
    }
}
