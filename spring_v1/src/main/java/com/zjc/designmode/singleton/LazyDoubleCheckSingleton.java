package com.zjc.designmode.singleton;

import java.util.Objects;

/**
 * @ClassName : LazzSingleton
 * @Author : zhangjiacheng
 * @Date : 2020/12/16
 * @Description : TODO
 */
public class LazyDoubleCheckSingleton {

    private volatile static LazyDoubleCheckSingleton lazy = null;

    private LazyDoubleCheckSingleton(){
        if (Objects.nonNull(lazy)){
            throw new RuntimeException("不允许创建多个实例");
        }
    }

    public static LazyDoubleCheckSingleton getInstance(){
        if (Objects.isNull(lazy)){
            synchronized (LazyDoubleCheckSingleton.class){
                if (Objects.isNull(lazy)){
                    lazy = new LazyDoubleCheckSingleton();
                }
            }
        }
        return lazy;
    }

}
