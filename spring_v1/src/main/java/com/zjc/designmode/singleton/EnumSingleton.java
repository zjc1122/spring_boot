package com.zjc.designmode.singleton;

/**
 * @ClassName : EnumSingleton
 * @Author : zhangjiacheng
 * @Date : 2020/12/16
 * @Description : TODO
 */
public enum  EnumSingleton {
    //
    INSTANCE;

    private Object data;

    public Object getData() {
        return data;
    }


    public static EnumSingleton getInstance(){
        return INSTANCE;
    }

    public void ss(){
        System.out.println(1);
    }
}
