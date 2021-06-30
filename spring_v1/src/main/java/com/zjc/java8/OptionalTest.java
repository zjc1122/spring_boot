package com.zjc.java8;

import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName : OptionalTest
 * @Author : zhangjiacheng
 * @Date : 2020/11/26
 * @Description : TODO
 */
public class OptionalTest {

    public static void main(String[] args) {
//        Optional<String> op = Optional.of("hello");
        Optional<String> op = Optional.empty();
//        if (op.isPresent()){
//            System.out.println(op.get());
//        }
        op.ifPresent(System.out::println);
        System.out.println(op.orElse("aa"));

        System.out.println(op.orElseGet(() -> "hhh"));

        Person p1 = new Person("zhangsan",11);
        Person p2 = new Person("lisi",22);
        List<Person> list = ImmutableList.of(p1, p2);
        Room room = new Room();
        room.setName("No1");
        room.setPersonList(list);

        Optional<Room> room1 = Optional.ofNullable(room);
        List list1 = room1.map(Room::getPersonList).orElse(Collections.EMPTY_LIST);
        System.out.println(list1);
    }
}
