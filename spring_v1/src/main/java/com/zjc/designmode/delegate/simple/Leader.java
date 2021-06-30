package com.zjc.designmode.delegate.simple;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Objects;

/**
 * @ClassName : Leader
 * @Author : zhangjiacheng
 * @Date : 2020/12/18
 * @Description : TODO
 */
public class Leader {

    Map<String, IEmployee> register = Maps.newHashMap();

    public Leader(){
        register.put("开发",new EmployeeA());
        register.put("架构",new EmployeeB());
    }
    public void doing(String command){
        IEmployee employee = register.get(command);
        if (Objects.isNull(employee)){
            System.out.println("这活没人能干的了，招人吧");
            return;
        }
        employee.doing(command);
    }
}
