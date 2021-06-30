package com.zjc.designmode.observer.guava;

import com.google.common.eventbus.EventBus;

/**
 * @ClassName : GuavaTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/24
 * @Description : TODO
 */
public class GuavaTest {

    public static void main(String[] args) {
        //消息总线
        EventBus eventBus = new EventBus();

        GuavaEvent guavaEvent = new GuavaEvent();
        eventBus.register(guavaEvent);
        eventBus.post("zjc");
    }
}
