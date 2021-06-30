package com.zjc.designmode.factory;

/**
 * @ClassName : PhoneFactory
 * @Author : zhangjiacheng
 * @Date : 2020/5/27
 * @Description : TODO
 */
public class AppleFactory extends AbstractFactory{
    @Override
    public IPC getPc() {
        return new MAC();
    }

    @Override
    public IPhone getPhone() {
        return new ApplePhone();
    }
}
