package com.zjc.designmode.delegate.simple;

/**
 * @ClassName : Boss
 * @Author : zhangjiacheng
 * @Date : 2020/12/18
 * @Description : TODO
 */
public class Boss {

    public void command(String command, Leader leader){
        leader.doing(command);
    }
}
