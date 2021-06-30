package com.zjc.designmode.proxy.jdkproxy;

/**
 * @ClassName : ProxyTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/17
 * @Description : TODO
 */
public class ProxyTest {

    public static void main(String[] args) {
        Order order = new Order();
        order.setCreatTime(System.currentTimeMillis());
        IOrderService instance = (IOrderService)new OrderServiceDynamicProxy().getInstance(new OrderService());
        instance.creatOrder(order);
    }
}
