package com.zjc.designmode.proxy.staticproxy;

/**
 * @ClassName : ProxyTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/24
 * @Description : TODO
 */
public class ProxyTest {

    public static void main(String[] args) {
        Son son = new Son();
        Father father = new Father(son);
        father.finLove();
    }
}
