package com.zjc.java8;

import java.util.List;

/**
 * @ClassName : Room
 * @Author : zhangjiacheng
 * @Date : 2020/11/26
 * @Description : TODO
 */
public class Room {

    private String name;

    private List<Person> personList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
