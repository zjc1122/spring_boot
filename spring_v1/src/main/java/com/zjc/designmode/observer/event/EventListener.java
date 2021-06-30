package com.zjc.designmode.observer.event;

import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName : EventLisenter
 * @Author : zhangjiacheng
 * @Date : 2020/12/23
 * @Description : TODO
 * 监听器，就是观察者
 */
public class EventListener {

    protected Map<String,Event> events = Maps.newHashMap();

    //时间名称和一个目标对象来触发事件
    public void addListener(String eventType, Object target){
        try{
            this.addListener(
                    eventType,
                    target,
                    target.getClass().getMethod("on" + toUpperFirstCase(eventType),Event.class));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addListener(String eventType, Object target, Method callback){
        //注册事件
        events.put(eventType,new Event(target,callback));
    }

    //触发，只要有动作就触发
    private void trigger(Event event){
        event.setSource(this);
        event.setTime(System.currentTimeMillis());

        try{
            //发起回调
            if (Objects.nonNull(event.getCallback())){
                //用反射调用他的回调函数
                event.getCallback().invoke(event.getTarget(),event);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //事件名称触发
    protected void trigger(String trigger){
        if (!this.events.containsKey(trigger)){
            return;
        }
        Event event = this.events.get(trigger);
        event.setTrigger(trigger);
        trigger(event);
    }

    //逻辑处理的方法，首字母大写
    private String toUpperFirstCase(String str){
        char[] chars = str.toCharArray();
        chars[0] -=32;
        return String.valueOf(chars);
    }
}
