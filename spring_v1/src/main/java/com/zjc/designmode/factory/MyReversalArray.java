package com.zjc.designmode.factory;

import org.junit.Test;

/**
 * @ClassName : MyTest
 * @Author : zhangjiacheng
 * @Date : 2020/5/28
 * @Description : TODO
 */
public class MyReversalArray {

    @Test
    public void test() throws Exception {

        AbstractFactory factory = FactoryProducer.getFactory("com.zjc.designmode.factory.XiaoMiFactory");
        factory.getPhone().makePhone();
        factory.getPc().makePc();
    }
}
