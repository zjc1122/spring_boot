package com.zjc.designmode.adapter.loginadapter.v2;

/**
 * @ClassName : LoginForQQAdapter
 * @Author : zhangjiacheng
 * @Date : 2020/12/22
 * @Description : TODO
 */
public class LoginForQQAdapter implements LoginAdapter{
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForQQAdapter;
    }

    @Override
    public String login(String name, Object adapter) {
        return name + "使用QQ登录成功";
    }
}
