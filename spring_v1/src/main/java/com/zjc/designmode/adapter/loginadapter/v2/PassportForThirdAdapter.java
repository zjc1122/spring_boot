package com.zjc.designmode.adapter.loginadapter.v2;


import com.zjc.designmode.adapter.loginadapter.SiginService;

/**
 * @ClassName : PassportForThirdAdapter
 * @Author : zhangjiacheng
 * @Date : 2020/12/22
 * @Description : TODO
 */
public class PassportForThirdAdapter extends SiginService implements IPassportForThird {

    @Override
    public String loginForToken(String name) {
        return processLogin(name,LoginForTokenAdapter.class);
    }

    @Override
    public String loginForQQ(String name) {
        return processLogin(name,LoginForQQAdapter.class);
    }

    private String processLogin(String key, Class<? extends LoginAdapter> clazz){
        try {
            LoginAdapter loginAdapter = clazz.newInstance();
            if (loginAdapter.support(loginAdapter)){
                return loginAdapter.login(key,loginAdapter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
