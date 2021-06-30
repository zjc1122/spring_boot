package com.zjc.thread;

/**
 * @ClassName : ThreadA
 * @Author : zhangjiacheng
 * @Date : 2021/2/5
 * @Description : TODO
 */
public class ThreadA extends Thread{

    private Object a;

    public ThreadA(Object a) {
        this.a = a;
    }

    @Override
    public void run() {
        synchronized (a){
            System.out.println("start A");
            try {
                a.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end A");
        }
    }
}
