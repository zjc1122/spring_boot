package com.zjc.arithmetic;


import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName : BlockQueue
 * @Author : zhangjiacheng
 * @Date : 2021/6/6
 * @Description : TODO
 */
public class BlockQueue {
    private static int count = 500;
    static LinkedList list = new LinkedList<String>();
    final ReentrantLock lock = new ReentrantLock();

    /** 不空，可以继续取 */
    private final Condition notEmpty = lock.newCondition();

    /** 不满，可以继续放*/
    private final Condition notFull = lock.newCondition();

    private void put(String str){
        lock.lock();
        try {
            while (count == list.size()){
                notFull.await();
            }
            list.add(str);
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    private String take(){
        lock.lock();
        try {
            while (list.size() == 0){
                notEmpty.await();
            }
//            System.out.println(list.removeFirst());
            notFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return (String) list.removeFirst();
    }

    public static void main(String[] args) {
        final BlockQueue mq = new BlockQueue();
//        System.out.println("初始化四个元素");
//        mq.put("aa");
//        mq.put("bb");
//        mq.put("cc");
//        mq.put("dd");

        Thread t1 = new Thread(() -> {
            System.out.println("小明开始放元素了");
            while (count > list.size()){
                System.out.println("小明放入ee" + count--);
                mq.put("小明放入ee" + count--);
            }
//                mq.put("ff");
//                System.out.println(list.size());
        });
        t1.start();

        Thread t2 = new Thread(() -> {

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("小王开始拿元素了");
            while (list.size()>0){
                String take = mq.take();
                System.out.println("小王取出"+take);
            }
        });
        t2.start();
}}
