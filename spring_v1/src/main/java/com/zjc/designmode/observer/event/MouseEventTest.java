package com.zjc.designmode.observer.event;

/**
 * @ClassName : MouseEventTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/23
 * @Description : TODO
 */
public class MouseEventTest {

    public static void main(String[] args) {

        MouseEventCallback callback = new MouseEventCallback();
        Mouse mouse = new Mouse();
        mouse.addListener(MouseEventType.CLICK,callback);
        mouse.click();
    }
}
