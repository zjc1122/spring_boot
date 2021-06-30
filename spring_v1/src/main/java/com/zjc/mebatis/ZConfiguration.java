package com.zjc.mebatis;

import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

/**
 * @ClassName : ZConfiguration
 * @Author : zhangjiacheng
 * @Date : 2021/1/31
 * @Description : TODO
 */
public class ZConfiguration {

    public final static ResourceBundle sqlMappings;
    static {
        sqlMappings = ResourceBundle.getBundle("mesql");
    }
    /**
     * 返回接口的代理类对象
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class clazz,ZSqlSession sqlSession) {
        return (T)Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{clazz},new ZMapperProxy(sqlSession));
    }
}
