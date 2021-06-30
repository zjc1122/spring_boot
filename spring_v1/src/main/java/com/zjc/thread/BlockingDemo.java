package com.zjc.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName : BlockingDemo
 * @Author : zhangjiacheng
 * @Date : 2021/3/4
 * @Description : TODO
 */
public class BlockingDemo {

    ArrayBlockingQueue<String> ab = new ArrayBlockingQueue<>(10);
    {
        init();
    }

    public void init(){
        new Thread(()->{
            while (true){
                try {
                    String take = ab.take();
                    System.out.println("receive:"+take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void add(String data) throws InterruptedException {
        ab.add(data);
        System.out.println("sendData:" + data);
        Thread.sleep(1000);
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingDemo blockingDemo=new BlockingDemo();
        for (int i = 0;i<10000;i++){
            blockingDemo.add("data"+i);
        }
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
    }
}
