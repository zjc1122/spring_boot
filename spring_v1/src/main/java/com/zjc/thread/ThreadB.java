package com.zjc.thread;

/**
 * @ClassName : ThreadA
 * @Author : zhangjiacheng
 * @Date : 2021/2/5
 * @Description : TODO
 */
public class ThreadB extends Thread{

    private Object b;

    public ThreadB(Object b) {
        this.b = b;
    }

    @Override
    public void run() {
        synchronized (b){
            System.out.println("start b");
            try {
                b.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("end b");
        }
    }
}
