package com.zjc.designmode.adapter.poweradapter;

/**
 * @ClassName : PowerTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/22
 * @Description : TODO
 */
public class PowerTest {

    public static void main(String[] args) {
        DC5 dc5 = new PowerAdapter(new AC220());
        System.out.println(dc5.outputDC5V());
    }
}
