package com.zjc.designmode.singleton;

import java.util.Objects;

/**
 * @ClassName : HungrySingleton
 * @Author : zhangjiacheng
 * @Date : 2020/12/16
 * @Description : TODO
 */
public class HungrySingleton {

    private static final HungrySingleton hungrySingleton;

    static {
        hungrySingleton = new HungrySingleton();
    }

    private HungrySingleton(){
        if (Objects.nonNull(hungrySingleton)){
            throw new RuntimeException("不允许创建多个实例");
        }
    }

    public static HungrySingleton getInstance(){
        return hungrySingleton;
    }
}
