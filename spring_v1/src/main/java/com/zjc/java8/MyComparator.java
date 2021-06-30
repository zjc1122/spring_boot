package com.zjc.java8;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @ClassName : MyComparator
 * @Author : zhangjiacheng
 * @Date : 2020/11/30
 * @Description : TODO
 */
public class MyComparator {
    public static void main(String[] args) {
        ArrayList<String> strings = Lists.newArrayList("nihao", "hello", "world", "welcome");
        Collections.sort(strings, Comparator.comparingInt(String::length).thenComparing(String::toLowerCase,Comparator.reverseOrder()));
        strings.sort(Comparator.comparingInt(String::length).reversed().thenComparing(String::toLowerCase,Comparator.reverseOrder()));

        System.out.println(strings);
    }
}
