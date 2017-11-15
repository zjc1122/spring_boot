package cn.zjc.test;


import cn.zjc.aspect.redisdistributedlock.RedisLock;
import cn.zjc.aspect.zkdistributedlock.ZkDistributedLock;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjiacheng on 2017/11/8.
 */
@Component
public class DistributedTest {

    private int i = 500;//假设有500个库存
    @RedisLock(value = "lock") //redis分布式锁
//    @ZkDistributedLock //zk分布式锁
    public void getGoods(String key, int key1) {

        System.out.println(Thread.currentThread().getName() + "开始秒杀");
        i--;
        if(i>0){
            System.out.println("库存还有"+ i + "个。");
        }else{
            System.out.println("已结束");
        }

    }
}
