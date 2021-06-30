package com.zjc.spring.action;

import com.zjc.spring.mvc.ZService;

/**
 * @ClassName : DemoService
 * @Author : zhangjiacheng
 * @Date : 2020/12/29
 * @Description : TODO
 */
@ZService
public class DemoService implements IDemoService{

    @Override
    public String get(String name){
        return "My name is " + name ;
    }

    @Override
    public String remove(Integer id, String name) throws Exception{
        throw new Exception("zjc throwing ");
    }
}
