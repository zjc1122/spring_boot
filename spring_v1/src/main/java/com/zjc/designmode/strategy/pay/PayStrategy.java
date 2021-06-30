package com.zjc.designmode.strategy.pay;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @ClassName : PayStrategy
 * @Author : zhangjiacheng
 * @Date : 2020/12/21
 * @Description : TODO
 */
public class PayStrategy {
    public static final String ALI_PAY = "Alipay";
    public static final String JD_PAY = "JDpay";
    public static final String UNION_PAY = "Wechatpay";
    public static final String WECHAT_PAY = "Unionpay";
    public static final String DEFAULT_PAY = ALI_PAY;

    private static Map<String,Payment> payMap = Maps.newHashMap();
    static {
        payMap.put(ALI_PAY,new AliPay());
        payMap.put(JD_PAY,new JDPay());
        payMap.put(UNION_PAY,new UnionPay());
        payMap.put(WECHAT_PAY,new WechatPay());
        payMap.put(DEFAULT_PAY,new AliPay());
    }

    public static Payment get(String payKey){
        if (!payMap.containsKey(payKey)){
            return payMap.get(DEFAULT_PAY);
        }
        return payMap.get(payKey);
    }
}
