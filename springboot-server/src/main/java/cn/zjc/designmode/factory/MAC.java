package cn.zjc.designmode.factory;

/**
 * @ClassName : Blue
 * @Author : zhangjiacheng
 * @Date : 2020/5/27
 * @Description : TODO
 */
public class MAC implements IPC {
    @Override
    public void makePc() {
        System.out.println("苹果电脑");
    }
}
