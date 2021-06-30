package com.zjc.spring.action;

/**
 * @ClassName : IDemoService
 * @Author : zhangjiacheng
 * @Date : 2020/12/29
 * @Description : TODO
 */
public interface IDemoService{

    String get(String name);

    String remove(Integer id,String name) throws Exception;
}
