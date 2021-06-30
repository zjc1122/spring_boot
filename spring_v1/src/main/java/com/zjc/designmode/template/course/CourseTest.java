package com.zjc.designmode.template.course;

/**
 * @ClassName : CourseTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/21
 * @Description : TODO
 */
public class CourseTest {
    public static void main(String[] args) {
        NetworkCourse javaCourse = new JavaCourse();
        javaCourse.createCourse();

        BigDataCourse bigDataCourse = new BigDataCourse(true);
        bigDataCourse.createCourse();
    }
}
