package com.zjc.designmode.strategy.promotion;

import org.springframework.stereotype.Service;

/**
 * @Author : zhangjiacheng
 * @ClassName : Model3ServiceImpl
 * @Date : 2019/12/23
 * @Description : TODO
 */
@Service
public class CashBackStrategy implements PromotionStrategy {
    @Override
    public void doPromotion() {
        System.out.println("返现促销，金额会转到支付宝账号");
    }
}
