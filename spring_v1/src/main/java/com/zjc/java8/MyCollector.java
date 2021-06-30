package com.zjc.java8;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @ClassName : MyCollector
 * @Author : zhangjiacheng
 * @Date : 2020/12/1
 * @Description : TODO
 */
public class MyCollector<T> implements Collector<T, Set<T>, Map<T,T>> {
    @Override
    public Supplier<Set<T>> supplier() {
        System.out.println("supplier invoked");
        return HashSet::new;
    }

    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        System.out.println("accumulator invoked");
        return (set,item) -> {
            System.out.println("accumulator: " + set + " " + Thread.currentThread().getName());
            set.add(item);
        };
    }

    /**
     * 并行流并且没有Characteristics.CONCURRENT参数才会被调用;
     * 如果存在Characteristics.CONCURRENT参数，并行流是多个线程操作一个中间结果容器
     * @return
     */
    @Override
    public BinaryOperator<Set<T>> combiner() {
        System.out.println("combiner invoked");
        return (set1,set2)->{
            System.out.println("set1" + set1);
            System.out.println("set2" + set2);
            set1.addAll(set2);
            return set1;
        };
    }

    @Override
    public Function<Set<T>, Map<T, T>> finisher() {
        System.out.println("finisher invoked");
        return set-> {
            HashMap<T, T> map = Maps.newHashMap();
            set.forEach(item -> map.put(item,item));
            return map;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        System.out.println("characteristics invoked");
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED));
    }

    public static void main(String[] args) {
        ArrayList<String> strings = Lists.newArrayList("aa", "bb", "cc", "hello", "world", "aa", "a","b","c","d","e");
        HashSet<String> objects = Sets.newHashSet(strings);
//        Map<String, String> collect = objects.stream().collect(new MyCollector<>());
        for (int i = 0;i<100;i++){
            Map<String, String> collect = objects.parallelStream().collect(new MyCollector<>());
            System.out.println(JSON.toJSONString(collect));
        }
    }
}
