package com.zjc.designmode.observer.gperadvice;

/**
 * @ClassName : ObserverTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/23
 * @Description : TODO
 */
public class ObserverTest {

    public static void main(String[] args) {
        Gper gper = Gper.getInstance();
        Teacher teacher = new Teacher("zjc");
        Question question = new Question();
        question.setUserName("aaa");
        question.setContent("设计模式的适用场景");
        gper.addObserver(teacher);
        gper.publishQuestion(question);

    }
}
