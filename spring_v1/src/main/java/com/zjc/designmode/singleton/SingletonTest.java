package com.zjc.designmode.singleton;


/**
 * @ClassName : SingletonTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/16
 * @Description : TODO
 */
public class SingletonTest {

    public static void main(String[] args) {
//        Thread t1 = new Thread(new ExecutorThread());
//        Thread t2 = new Thread(new ExecutorThread());
//
//        t1.start();
//        t2.start();
//        try {
//            Class<?> clazz = HungrySingleton.class;
//            Constructor c = clazz.getDeclaredConstructor(null);
//            c.setAccessible(true);
//            Object o = c.newInstance();
//            Object instance = HungrySingleton.getInstance();
//            System.out.println(o == instance);q
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        EnumSingleton instance = EnumSingleton.getInstance();
        System.out.println(instance.getClass());

        Object bean = ContainerSingleton.getBean("com.zjc.designmode.singleton.Student");
        System.out.println(bean);

        System.out.println(ThreadLocalSingleton.getInstance());
    }
}
