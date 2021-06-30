package com.zjc.designmode.observer.event;

import java.lang.reflect.Method;

/**
 * @ClassName : Event
 * @Author : zhangjiacheng
 * @Date : 2020/12/23
 * @Description : TODO
 * 事件格式定义
 */
public class Event {

    //事件源，事件是由谁发起的保存起来
    private Object source;
    //事件触发，要通知谁
    private Object target;
    //事件触发，要做什么动作，回调
    private Method callback;
    //事件的名称，触发的是什么事件
    private String trigger;
    //事件触发的时间
    private long time;

    public Event(Object target, Method callback) {
        this.target = target;
        this.callback = callback;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getCallback() {
        return callback;
    }

    public void setCallback(Method callback) {
        this.callback = callback;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Event{" + "\n"+
                "source=" + source + ", \n"+
                "target=" + target + ", \n"+
                "callback=" + callback + ", \n"+
                "trigger='" + trigger + '\'' + ", \n"+
                "time=" + time + "\n"+
                '}';
    }
}
