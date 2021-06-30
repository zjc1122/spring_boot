package com.zjc.designmode.observer.gperadvice;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者
 * @ClassName : Teacher
 * @Author : zhangjiacheng
 * @Date : 2020/12/23
 * @Description : TODO
 */
public class Teacher implements Observer {

    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        Gper gper = (Gper) o;
        Question question = (Question)arg;
        System.out.println("=========================");
        System.out.println(name + "老师，你好！\n" +
                "您收到一个来自“"+ gper.getName()+ "”的提问，希望您解答，问题内容如下：\n" +
                question.getContent() + "\n" + "提问者：" +question.getUserName());
    }
}
