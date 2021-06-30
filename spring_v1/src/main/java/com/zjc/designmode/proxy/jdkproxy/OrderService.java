package com.zjc.designmode.proxy.jdkproxy;

/**
 * @ClassName : OrderService
 * @Author : zhangjiacheng
 * @Date : 2020/12/17
 * @Description : TODO
 */
public class OrderService implements IOrderService{

    @Override
    public int creatOrder(Order order) {
        System.out.println(order.getCreatTime()+":开始处理");
        return 0;
    }
}
