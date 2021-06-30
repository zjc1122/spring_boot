package com.zjc.designmode.delegate.simple;

/**
 * @ClassName : DelegateTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/18
 * @Description : TODO
 */
public class DelegateTest {

    public static void main(String[] args) {
        new Boss().command("开发",new Leader());
    }
}
