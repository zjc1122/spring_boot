package com.zjc.designmode.strategy.promotion;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @ClassName : PromotionStrategyFactory
 * @Author : zhangjiacheng
 * @Date : 2020/12/19
 * @Description : TODO
 */
public class PromotionStrategyFactory {

    private static Map<String, PromotionStrategy> PROMOTION_MAP = Maps.newHashMap();
    static {
        PROMOTION_MAP.put(PromotionKey.NONE, new EmptyStrategy());
        PROMOTION_MAP.put(PromotionKey.COUPON, new CouponStrategy());
        PROMOTION_MAP.put(PromotionKey.CASH, new CashBackStrategy());
        PROMOTION_MAP.put(PromotionKey.GROUP, new GroupBuyStrategy());
    }
    private PromotionStrategyFactory(){}

    public static PromotionStrategy getPromotionStrategy(String promotion){
        PromotionStrategy strategy = PROMOTION_MAP.get(promotion);
        return strategy == null ? new EmptyStrategy() : strategy;
    }

    private interface PromotionKey{
        String NONE = "none";
        String COUPON = "coupon";
        String CASH = "cash";
        String GROUP = "group";
    }
}
