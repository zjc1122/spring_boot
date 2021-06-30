package com.zjc.designmode.singleton;

/**
 * @ClassName : ExectorThread
 * @Author : zhangjiacheng
 * @Date : 2020/12/16
 * @Description : TODO
 */

public class ExecutorThread implements Runnable{

    @Override
    public void run() {
        LazyDoubleCheckSingleton lazySimpleSingleton = LazyDoubleCheckSingleton.getInstance();
        System.out.println(Thread.currentThread().getName() + ":" + lazySimpleSingleton);
    }
}
