package com.zjc.designmode.adapter.loginadapter.v2;

/**
 * @ClassName : LoginForQQAdapter
 * @Author : zhangjiacheng
 * @Date : 2020/12/22
 * @Description : TODO
 */
public class LoginForTokenAdapter implements LoginAdapter{
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForTokenAdapter;
    }

    @Override
    public String login(String name, Object adapter) {
        return name + "Token登录成功";
    }
}
