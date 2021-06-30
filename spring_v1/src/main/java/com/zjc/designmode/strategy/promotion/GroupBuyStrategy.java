package com.zjc.designmode.strategy.promotion;

import org.springframework.stereotype.Service;

/**
 * @Author : zhangjiacheng
 * @ClassName : Model3ServiceImpl
 * @Date : 2019/12/23
 * @Description : TODO
 */
@Service
public class GroupBuyStrategy implements PromotionStrategy {
    @Override
    public void doPromotion() {
        System.out.println("拼团，满2人成团，全团享受团购价");
    }
}
