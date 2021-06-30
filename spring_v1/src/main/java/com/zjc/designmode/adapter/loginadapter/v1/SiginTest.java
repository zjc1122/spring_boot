package com.zjc.designmode.adapter.loginadapter.v1;

/**
 * @ClassName : SinginTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/22
 * @Description : TODO
 */
public class SiginTest {

    public static void main(String[] args) {
        SiginForThirdService sigin = new SiginForThirdService();
        sigin.login("zzz");
        sigin.loginForQQ("ggg");
        sigin.loginForToken("555");
        sigin.register("777");
    }
}
