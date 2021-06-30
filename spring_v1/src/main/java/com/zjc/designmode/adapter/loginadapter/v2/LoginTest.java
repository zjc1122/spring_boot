package com.zjc.designmode.adapter.loginadapter.v2;

/**
 * @ClassName : LoginTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/22
 * @Description : TODO
 */
public class LoginTest {

    public static void main(String[] args) {
        PassportForThirdAdapter passport = new PassportForThirdAdapter();
        String zzz = passport.loginForQQ("zzz");
        System.out.println(zzz);
    }
}
