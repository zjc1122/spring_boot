package com.zjc.java8;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @ClassName : MethodReferenceTest
 * @Author : zhangjiacheng
 * @Date : 2020/11/26
 * @Description : TODO
 */
public class MethodReferenceTest{

    public static void main(String[] args) {
        new Thread(() -> System.out.println("创建一个线程")).start();
        System.out.println("-----------jdk自带排序--------------");
        List<String> strings = Lists.newArrayList("hello", "baa","shanghai","liangshan");
//        Collections.sort(strings,(a,b)->a.compareToIgnoreCase(b));
//        Collections.sort(strings,String::compareToIgnoreCase);
        strings.sort(String::compareToIgnoreCase);
        strings.forEach(System.out::println);


        Student s1 = new Student("zhangsan", 22,22);
        Student s2 = new Student("lisi", 66,33);
        Student s3 = new Student("wangwu", 88,44);
        Student s4 = new Student("zhaoliu", 55,55);
        List<Student> students = Arrays.asList(s1, s2, s3, s4);
        System.out.println("-----------类名::静态方法名--------------");
        students.sort(Comparator.comparingInt(Student::getScore));
        students.sort(Student::compareStudentByScore);
        students.forEach(s -> System.out.println(s.getScore()));
        students.sort(Student::compareStudentByName);
        students.forEach(s -> System.out.println(s.getName()));
        System.out.println("-----------类的实例::实例方法名--------------");
        StudentComparator sc = new StudentComparator();
        students.sort(sc::compareByScore);
        students.forEach(s -> System.out.println(s.getScore()));
        students.sort(sc::compareByName);
        students.forEach(s -> System.out.println(s.getName()));
        System.out.println("----------类名::实例方法名---------------");
        students.sort(Student::compareByName);
        students.forEach(s -> System.out.println(s.getName()));
        students.sort(Student::comparteByScore);
        students.forEach(s -> System.out.println(s.getScore()));
        System.out.println("----------类名::new---------------");
        MethodReferenceTest mt = new MethodReferenceTest();
        System.out.println(mt.getString(String::new));
        System.out.println(mt.getString2("hello", String::new));


    }

    public String getString(Supplier<String> supplier){
        return supplier.get() + "test";
    }

    public String getString2(String str, Function<String,String> function){
        return function.apply(str);
    }


}
