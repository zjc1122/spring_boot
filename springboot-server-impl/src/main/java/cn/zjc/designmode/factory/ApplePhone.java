package cn.zjc.designmode.factory;

/**
 * @ClassName : XiaoMi
 * @Author : zhangjiacheng
 * @Date : 2020/5/27
 * @Description : TODO
 */
public class ApplePhone implements IPhone{

    @Override
    public void makePhone() {
        System.out.println("苹果手机");
    }
}
