package cn.zjc.designmode.factory;

/**
 * @ClassName : XiaoMi
 * @Author : zhangjiacheng
 * @Date : 2020/5/27
 * @Description : TODO
 */
public class XiaoMiPhone implements IPhone{

    @Override
    public void makePhone() {
        System.out.println("小米手机");
    }
}
