package com.zjc.java8;

import javax.swing.*;

/**
 * @ClassName : SwingTest
 * @Author : zhangjiacheng
 * @Date : 2020/11/21
 * @Description : TODO
 */
public class SwingTest {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("my");
        JButton jButton = new JButton("button");
//        jButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("点我一下试试");
//            }
//        });
        jButton.addActionListener(a -> System.out.println("点我一下试试"));
        jFrame.add(jButton);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Runnable r = ()->{};
        System.out.println(r.getClass().getInterfaces()[0]);
        new Thread(()-> System.out.println("hello")).start();
    }
}
