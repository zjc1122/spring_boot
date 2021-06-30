package com.zjc.designmode.template.course;

/**
 * @ClassName : NetwordCourse
 * @Author : zhangjiacheng
 * @Date : 2020/12/21
 * @Description : TODO
 */
public abstract class NetworkCourse {

    protected final void createCourse(){
        //1.发布预习资料
        this.postPreResource();
        //2.制作PPT课件
        this.createPPT();
        //3.在线直播
        this.liveVideo();
        //4.布置作业，有作业的课程需要检查作业，没作业的课程则直接完成
        if (needHomework()){
            checkHomework();
        }
    }

    abstract void checkHomework();

    //钩子方法:实现流程的微调
    protected boolean needHomework(){
        return false;
    }

    final void liveVideo(){
        System.out.println("直播授课");
    }

    final void createPPT(){
        System.out.println("制作ppt课件");
    }

    final void postPreResource(){
        System.out.println("分发预习资料");
    }
}
