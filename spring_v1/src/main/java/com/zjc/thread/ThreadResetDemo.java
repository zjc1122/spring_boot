package com.zjc.thread;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName : ThreadResetDemo
 * @Author : zhangjiacheng
 * @Date : 2021/2/3
 * @Description : TODO
 */
public class ThreadResetDemo {

    /**
     * interrupt复位
     * @param args
     * @throws InterruptedException
     */
//    public static void main(String[] args) throws InterruptedException {
//        Thread thread = new Thread(()->{
//            while (true){
//                if (Thread.currentThread().isInterrupted()){
//                    System.out.println("before:" + Thread.currentThread().isInterrupted());
//                    Thread.interrupted();
//                    System.out.println("after:" + Thread.currentThread().isInterrupted());
//                }
//            }
//        });
//        thread.start();
//
//        TimeUnit.SECONDS.sleep(1);
//        thread.interrupt();
//    }


    /**
     * 异常复位
     * @param args
     * @throws InterruptedException
     */
    private static int i;
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(()->{
            while (!Thread.currentThread().isInterrupted()){//默认是false
                i++;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;//中断线程
                }
            }
            System.out.println("i" + i);
        });
        thread.start();

        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();
        System.out.println(thread.isInterrupted()); //true


        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();

    }

}
