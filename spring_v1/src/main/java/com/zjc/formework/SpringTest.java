package com.zjc.formework;

import com.zjc.formework.context.ZApplicationContext;
import com.zjc.spring.action.DemoService;

/**
 * @ClassName : SpringTest
 * @Author : zhangjiacheng
 * @Date : 2021/1/11
 * @Description : TODO
 */
public class SpringTest {

    public static void main(String[] args) {
        ZApplicationContext context = new ZApplicationContext("classpath:application.properties");
        Object demoService = context.getBean(DemoService.class);
        System.out.println(demoService);
        System.out.println(context);
    }
}
