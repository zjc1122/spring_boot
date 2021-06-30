package com.zjc.designmode.adapter.loginadapter.v2;

/**
 * @ClassName : LoginAdapter
 * @Author : zhangjiacheng
 * @Date : 2020/12/22
 * @Description : TODO
 */
public interface LoginAdapter {

    boolean support(Object adapter);

    String login(String name, Object adapter);
}
