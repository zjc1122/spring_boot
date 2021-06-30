package com.zjc.designmode.singleton;

import java.util.Objects;

/**
 * @ClassName : LazzSingleton
 * @Author : zhangjiacheng
 * @Date : 2020/12/16
 * @Description : TODO
 */
public class LazySimpleSingleton {

    private static LazySimpleSingleton lazy = null;

    private LazySimpleSingleton(){}

    public synchronized static LazySimpleSingleton getInstance(){
        if (Objects.isNull(lazy)){
            lazy = new LazySimpleSingleton();
        }
        return lazy;
    }

}
