package com.zjc.designmode.delegate.simple;

/**
 * @ClassName : EmployeeA
 * @Author : zhangjiacheng
 * @Date : 2020/12/18
 * @Description : TODO
 */
public class EmployeeA implements IEmployee{
    @Override
    public void doing(String command) {
        System.out.println("我是员工A,我可以干" + command);
    }
}
