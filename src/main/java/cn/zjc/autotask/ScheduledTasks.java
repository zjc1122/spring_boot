package cn.zjc.autotask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @ClassName : ScheduledTasks
 * @author : zhangjiacheng
 * @date : 2018/6/11
 * @Description : 定时任务类
 */
@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Scheduled(cron = "0 0/1 23 * * ?")
    public void autoTask() {
        System.out.println("开始自动任务");
        logger.info("开始自动任务");
    }
}
