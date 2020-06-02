package cn.zjc.designmode.factory;

import org.junit.Test;

/**
 * @ClassName : MyTest
 * @Author : zhangjiacheng
 * @Date : 2020/5/28
 * @Description : TODO
 */
public class MyTest {

    @Test
    public void test() throws Exception {

        AbstractFactory factory = FactoryProducer.getFactory("cn.zjc.designmode.factory.XiaoMiFactory");
        factory.getPhone().makePhone();
        factory.getPc().makePc();
    }
}
