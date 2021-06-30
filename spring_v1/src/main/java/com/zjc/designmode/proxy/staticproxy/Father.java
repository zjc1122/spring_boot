package com.zjc.designmode.proxy.staticproxy;

/**
 * @ClassName : Cook
 * @Author : zhangjiacheng
 * @Date : 2020/12/17
 * @Description : TODO
 */
public class Father implements Person{

    private Son son;

    public Father(Son son) {
        this.son = son;
    }

    @Override
    public void finLove() {
        System.out.println("开始给儿子找对象");
        son.finLove();
        System.out.println("找到合适的对象了");
    }
}
