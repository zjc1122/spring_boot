package com.zjc.java8;

/**
 * @ClassName : StudentComparator
 * @Author : zhangjiacheng
 * @Date : 2020/11/26
 * @Description : TODO
 */
public class StudentComparator {

    public int compareByScore(Student s1, Student s2){
        return s1.getScore() - s2.getScore();
    }
    public int compareByName(Student s1, Student s2){
        return s1.getName().compareToIgnoreCase(s2.getName());
    }
}
