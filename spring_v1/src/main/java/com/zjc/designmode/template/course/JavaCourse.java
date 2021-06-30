package com.zjc.designmode.template.course;

/**
 * @ClassName : JavaCourse
 * @Author : zhangjiacheng
 * @Date : 2020/12/21
 * @Description : TODO
 */
public class JavaCourse extends NetworkCourse {
    @Override
    void checkHomework() {
        System.out.println("检查java作业");
    }
}
