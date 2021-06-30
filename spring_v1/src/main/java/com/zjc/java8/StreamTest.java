package com.zjc.java8;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName : StreamTest
 * @Author : zhangjiacheng
 * @Date : 2020/11/27
 * @Description : TODO
 */
public class StreamTest {

    public static void main(String[] args) {
        Stream.iterate(1 ,a -> a+1).limit(2).forEach(System.out::println);
        Stream<String> stream = Stream.of("aaa", "bbb", "hello");
//        String[] strings = stream.toArray(a -> new String[a]);
        String[] strings = stream.toArray(String[]::new);
        Arrays.asList(strings).forEach(System.out::println);
        List<String> arrayList = stream.collect(Collectors.toCollection(ArrayList::new));
        arrayList.forEach(System.out::println);
        String s = stream.collect(Collectors.joining());
        System.out.println(s);

        List<String> objects = Lists.newArrayList("aaa","bbb","ccc");
        objects.stream().map(String::toUpperCase).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("--------------------------");
        List<Integer> integers = Lists.newArrayList(3,6,8);
        integers.stream().map(a -> a * a).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("--------------------------");
        Stream<List<Integer>> listStream = Stream.of(Arrays.asList(1,9), Arrays.asList(3, 4), Arrays.asList(6, 88));
        listStream.flatMap(Collection::stream).map(a -> a * a).forEach(System.out::println);
        Stream<String> generate = Stream.generate(UUID.randomUUID()::toString);
        generate.findFirst().ifPresent(System.out::println);

        ArrayList<String> list = Lists.newArrayList();
        for (int i =0;i<5000000;i++){
            list.add(UUID.randomUUID().toString());
        }
        System.out.println("开始排序");
        long start = System.nanoTime();
//        list.stream().sorted().count();
        list.parallelStream().sorted().count();
        long end = System.nanoTime();
        long l = TimeUnit.NANOSECONDS.toMillis(end - start);
        System.out.println(l);
        System.out.println("-----------分组-----------");
        Student st1 = new Student("zhangsan",100,20);
        Student st2 = new Student("lisi",30,55);
        Student st3 = new Student("wangwu",80,20);
        Student st4 = new Student("zhangsan",70,46);
        List<Student> students = Lists.newArrayList(st1, st2, st3, st4);
        Map<String, List<Student>> collect = students.stream().collect(Collectors.groupingBy(Student::getName));
        Map<String, Long> collect1 = students.stream().collect(Collectors.groupingBy(Student::getName, Collectors.counting()));
        Map<String, Double> collect2 = students.stream().collect(Collectors.groupingBy(Student::getName, Collectors.averagingDouble(Student::getScore)));
        System.out.println(JSON.toJSONString(collect));
        System.out.println(JSON.toJSONString(collect1));
        System.out.println(JSON.toJSONString(collect2));
        System.out.println("-----------分区-----------");
        Map<Boolean, List<Student>> collect3 = students.stream().collect(Collectors.partitioningBy(a -> a.getScore() >= 70));
        System.out.println(JSON.toJSONString(collect3));
        //关闭流操作,异常压制
        List<String> hello = Lists.newArrayList("hello", "world", "aaaa");
        try (Stream<String> stream1 = hello.stream()){
            stream1.onClose(()->{
                System.out.println("哈哈哈");
                throw new NullPointerException("first");
            }).onClose(()->{
                System.out.println("啦啦啦");
                throw new ArithmeticException("second");
            }).onClose(()->{
                System.out.println("嘿嘿嘿");
                throw new ArrayIndexOutOfBoundsException();
            }).forEach(System.out::println);
        }
    }
}
