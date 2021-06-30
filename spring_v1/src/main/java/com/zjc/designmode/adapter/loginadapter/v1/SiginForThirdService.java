package com.zjc.designmode.adapter.loginadapter.v1;

import com.zjc.designmode.adapter.loginadapter.SiginService;

/**
 * @ClassName : SinginService
 * @Author : zhangjiacheng
 * @Date : 2020/12/22
 * @Description : TODO
 */
public class SiginForThirdService extends SiginService {

    public void loginForToken(String name){
        System.out.println(name + "使用token登录成功");
    }

    public void loginForQQ(String name){
        System.out.println(name + "使用QQ登录成功");
    }

    @Override
    public void register(String name){
        super.register(name);
    }
}
