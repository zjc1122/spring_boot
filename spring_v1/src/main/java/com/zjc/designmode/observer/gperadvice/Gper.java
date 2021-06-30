package com.zjc.designmode.observer.gperadvice;

import java.util.Objects;
import java.util.Observable;

/**
 * JDK提供的观察者方式
 * 被观察者
 * @ClassName : Gper
 * @Author : zhangjiacheng
 * @Date : 2020/12/23
 * @Description : TODO
 */
public class Gper extends Observable {

    private String name = "咕泡生态圈";
    private static Gper gper = null;

    private Gper(){}

    public static Gper getInstance(){
        if (Objects.isNull(gper)){
            gper = new Gper();
        }
        return gper;
    }

    public String getName() {
        return name;
    }
    public void publishQuestion(Question question){
        System.out.println(question.getUserName() + "在" + this.name + "提交了一个问题。");
        setChanged();
        notifyObservers(question);
    }
}
