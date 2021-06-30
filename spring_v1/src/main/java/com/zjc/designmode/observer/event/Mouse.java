package com.zjc.designmode.observer.event;

/**
 * @ClassName : Mouse
 * @Author : zhangjiacheng
 * @Date : 2020/12/23
 * @Description : TODO
 */
public class Mouse extends EventListener{

    public void click(){
        System.out.println("调用单击方法");
        this.trigger(MouseEventType.CLICK);
    }

    public void move(){
        System.out.println("调用移动方法");
        this.trigger(MouseEventType.ON_MOVE);
    }
}
