package com.zjc.designmode.proxy.cglibproxy;

/**
 * @ClassName : CglibTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/17
 * @Description : TODO
 */
public class CglibTest {
    /**
     * jdk 实现了被代理对象的接口
     * CGlib 继承被代理对象，是覆盖父类方法
     * 目的：都是生成一个新的类，去实现增强代码逻辑的功能
     *
     * jdk 对于用户而言必须要有一个接口实现，目标类相对复杂
     * CGlib 可以代理任意一个类，没有任何要求
     *
     * jdk和CGlib都是生成字节码，jkd直接写class的字节码（效率高），CGlib使用ASM框架写字节码（效率低）
     * jdk 生成代理的逻辑简单，生成代理类效率高，执行效率低，每次都需要反射
     * CGlib 生成代理逻辑复杂，生成代理类效率低，执行效率高，生成一个包含所有逻辑的FastClass,不需要反射调用
     *
     * CGlib 不能代理final修饰的方法
     */
    public static void main(String[] args) {

        try {
            Customer obj = (Customer) new CGlibMeipo().getInstance(Customer.class);
//            System.out.println(obj);
            obj.findLove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
