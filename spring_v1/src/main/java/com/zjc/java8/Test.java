package com.zjc.java8;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @ClassName : Test
 * @Author : zhangjiacheng
 * @Date : 2020/11/23
 * @Description : TODO
 */
public class Test {

    public static void main(String[] args) {
        List<String> list = ImmutableList.of("hello","word","look");
        list.forEach(a-> System.out.println(a.toUpperCase()));
        list.forEach(System.out::println);
        list.stream().map(String::toUpperCase).forEach(System.out::println);
        List<String> collect = list.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(collect);

        List<Integer> list2 = ImmutableList.of(1,2,3,0);
        list2 = list2.stream().filter(a -> a == 0).collect(Collectors.toList());
        System.out.println(list2);

        Test test = new Test();
        System.out.println(test.compute(2, a -> a * 2));
        System.out.println(test.convert(4, a -> a + "aaa"));

        System.out.println(test.compose(2, value -> value * 3, v -> v * v));
        System.out.println(test.andThen(2, value -> value * 3, v -> v * v));
        System.out.println(test.compose2(2, 3, Integer::sum));
        System.out.println(test.compose2(3, 5, (a, b) -> a - b));

        Person person1 = new Person("zhangsan", 22);
        Person person2 = new Person("lisi", 33);
        Person person3 = new Person("wangwu", 44);

        List<Person> list4 = ImmutableList.of(person1, person2, person3);
        List<Person> byAge = test.getByAge(30, list4, (age, ps) -> list4.stream().filter(a -> a.getAge() > age).collect(Collectors.toList()));
        byAge.forEach(a -> System.out.println(a.getAge()));

        Predicate<String> predicate = p -> p.length() > 6;
        System.out.println(predicate.test("aaa"));

        Supplier<Integer> supplier = () -> 1+1;
        System.out.println(supplier.get());
    }

    public int compute(int a, Function<Integer,Integer> function){
        return function.apply(a);
    }

    public String convert(int a, Function<Integer,String> function){
        return function.apply(a);
    }

    public int compose(int a, Function<Integer,Integer> function1, Function<Integer,Integer> function2){
        return function1.compose(function2).apply(a);
    }

    public int andThen(int a, Function<Integer,Integer> function1, Function<Integer,Integer> function2){
        return function1.andThen(function2).apply(a);
    }

    public int compose2(int a, int b, BiFunction<Integer,Integer,Integer> function){
        return function.apply(a,b);
    }

    public List<Person> getByAge(int age, List<Person> persons, BiFunction<Integer,List<Person>,List<Person>> biFunction){
        return biFunction.apply(age,persons);
    }
}
