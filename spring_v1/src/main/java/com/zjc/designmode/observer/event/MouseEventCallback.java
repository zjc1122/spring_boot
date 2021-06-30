package com.zjc.designmode.observer.event;

/**
 * @ClassName : MouseEventCallback
 * @Author : zhangjiacheng
 * @Date : 2020/12/23
 * @Description : TODO
 */
public class MouseEventCallback {

    public void onClick(Event e){
        System.out.println("触发鼠标单击事件" + "\n" +e);
    }

    public void onMove(Event e){
        System.out.println("触发鼠标移动事件" + "\n" +e);
    }
}
