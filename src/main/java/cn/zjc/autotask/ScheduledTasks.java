package cn.zjc.autotask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * Created by zhangjiacheng on 2017/11/26
 */
@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Scheduled(cron="0 0/1 23 * * ?")
    public void autoTask(){
        System.out.println("开始自动任务");
        log.info("开始自动任务");
    }
}
