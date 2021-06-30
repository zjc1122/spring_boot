package com.zjc.designmode.adapter.poweradapter;

/**
 * @ClassName : AC220
 * @Author : zhangjiacheng
 * @Date : 2020/12/22
 * @Description : TODO
 */
public class AC220 {

    public int outputAC220V(){
        int output = 220;
        System.out.println("输出电流" + output + "V");
        return output;
    }
}
