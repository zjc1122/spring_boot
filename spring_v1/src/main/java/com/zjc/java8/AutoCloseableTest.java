package com.zjc.java8;

/**
 * @ClassName : AutoCloseableTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/1
 * @Description : TODO
 */
public class AutoCloseableTest implements AutoCloseable{

    @Override
    public void close() {
        System.out.println("colse invoked");
    }

    public void doSome(){
        System.out.println("aa");
    }

    public static void main(String[] args) throws Exception {
        try(AutoCloseableTest t = new AutoCloseableTest()){
            t.doSome();
        }
    }
}
