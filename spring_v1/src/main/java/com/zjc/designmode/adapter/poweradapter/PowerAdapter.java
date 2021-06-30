package com.zjc.designmode.adapter.poweradapter;

/**
 * @ClassName : PowerAdapter
 * @Author : zhangjiacheng
 * @Date : 2020/12/22
 * @Description : TODO
 */
public class PowerAdapter implements DC5{

    private AC220 ac220;

    public PowerAdapter(AC220 ac220) {
        this.ac220 = ac220;
    }

    @Override
    public int outputDC5V() {
        int adapterInput = ac220.outputAC220V();
        int adapterOutput = adapterInput /44;
        System.out.println("使用powerAdapter输入AC：" + adapterInput + "V,输出DC："+ adapterOutput + "V");
        return adapterOutput;
    }
}
