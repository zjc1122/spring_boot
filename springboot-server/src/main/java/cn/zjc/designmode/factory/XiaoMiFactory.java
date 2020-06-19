package cn.zjc.designmode.factory;

/**
 * @ClassName : PhoneFactory
 * @Author : zhangjiacheng
 * @Date : 2020/5/27
 * @Description : TODO
 */
public class XiaoMiFactory extends AbstractFactory{
    @Override
    public IPC getPc() {
        return new XiaoMiPC();
    }

    @Override
    public IPhone getPhone() {

        return new XiaoMiPhone();
    }
}
