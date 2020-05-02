package cn.zjc.test;


import cn.zjc.aspect.redisdistributedlock.RedisLock;
import org.springframework.stereotype.Component;

/**
 * @author : zhangjiacheng
 * @ClassName : DistributedTest
 * @date : 2018/6/15
 * @Description : 分布式锁测试类
 */
@Component
public class DistributedTest {
    /**
     * 假设有500个库存
     */
    int i = 500;

    //redis分布式锁
    @RedisLock(value = "lock")
    //zk分布式锁
//    @ZkDistributedLock
    public void getGoods(String key, int key1) {

        System.out.println(Thread.currentThread().getName() + "开始秒杀");
        i--;
        if (i > 0) {
            System.out.println("库存还有" + i + "个。");
        } else {
            System.out.println("已结束");
        }

    }
}
