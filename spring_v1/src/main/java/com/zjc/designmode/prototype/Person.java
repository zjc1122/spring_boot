package com.zjc.designmode.prototype;


import java.util.Objects;

/**
 * @ClassName : Person
 * @Author : zhangjiacheng
 * @Date : 2020/12/26
 * @Description : TODO
 * 原型模式就是实现Cloneable接口，重写clone方法
 */
public class Person implements Cloneable{
    int age = 18;
    int score = 99;
    Location localtion = new Location("bj","bb");

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Person p = (Person)super.clone();
        p.localtion = (Location) localtion.clone();
        return p;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Person p1 = new Person();
        Person p2 = (Person) p1.clone();
        System.out.println(p1.localtion == p2.localtion);
        p2.setScore(11);
        System.out.println(p1.localtion.name);
        System.out.println(p2.localtion.name);
    }
}

class Location implements Cloneable{
    String name;
    String addr;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(name, location.name) &&
                Objects.equals(addr, location.addr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, addr);
    }

    public Location(String name, String addr) {
        this.name = name;
        this.addr = addr;

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }
}


