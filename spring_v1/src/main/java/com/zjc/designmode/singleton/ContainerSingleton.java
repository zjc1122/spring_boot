package com.zjc.designmode.singleton;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @ClassName : ConainerSingleton
 * @Author : zhangjiacheng
 * @Date : 2020/12/16
 * @Description : TODO
 */
public class ContainerSingleton {

    private ContainerSingleton(){}

    private static final Map<String,Object> IOC = Maps.newConcurrentMap();

    public static Object getBean(String className){

        synchronized (IOC){
            if (!IOC.containsKey(className)){
                Object obj;
                try {
                    obj = Class.forName(className).newInstance();
                    IOC.put(className,obj);
                    return obj;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return IOC.get(className);
        }
    }
}
