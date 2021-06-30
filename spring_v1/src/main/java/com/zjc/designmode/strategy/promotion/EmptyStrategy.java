package com.zjc.designmode.strategy.promotion;

import org.springframework.stereotype.Service;

/**
 * @Author : zhangjiacheng
 * @ClassName : Model1ServiceImpl
 * @Date : 2019/12/21
 * @Description : TODO
 */
@Service
public class EmptyStrategy implements PromotionStrategy {

    @Override
    public void doPromotion(){
        System.out.println("无促销活动");
    }
}
