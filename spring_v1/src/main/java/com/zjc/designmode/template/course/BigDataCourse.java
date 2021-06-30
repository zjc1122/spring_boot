package com.zjc.designmode.template.course;

/**
 * @ClassName : BigDataCourse
 * @Author : zhangjiacheng
 * @Date : 2020/12/21
 * @Description : TODO
 */
public class BigDataCourse extends NetworkCourse {

    private boolean needHomework;

    public BigDataCourse(boolean needHomework) {
        this.needHomework = needHomework;
    }

    @Override
    void checkHomework() {
        System.out.println("检查大数据作业");
    }

    @Override
    protected boolean needHomework() {
        return this.needHomework;
    }
}
