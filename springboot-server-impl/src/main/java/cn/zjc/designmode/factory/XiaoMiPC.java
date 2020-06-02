package cn.zjc.designmode.factory;

/**
 * @ClassName : Blue
 * @Author : zhangjiacheng
 * @Date : 2020/5/27
 * @Description : TODO
 */
public class XiaoMiPC implements IPC {
    public XiaoMiPC() {
    }

    @Override
    public void makePc() {
        System.out.println("小米电脑");
    }
}
