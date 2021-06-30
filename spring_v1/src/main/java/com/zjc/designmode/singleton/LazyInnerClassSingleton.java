package com.zjc.designmode.singleton;

import java.util.Objects;

/**
 * @ClassName : lazyInnerClassSingleton
 * @Author : zhangjiacheng
 * @Date : 2020/12/16
 * @Description : TODO
 */
public class LazyInnerClassSingleton {

    private LazyInnerClassSingleton(){
        if (Objects.nonNull(LazyHolder.LAZY)){
            throw new RuntimeException("不允许创建多个实例");
        }
    }

    public static final LazyInnerClassSingleton getInstance(){
        return LazyHolder.LAZY;
    }

    private static class LazyHolder{
        private static final LazyInnerClassSingleton LAZY = new LazyInnerClassSingleton();
    }
}
