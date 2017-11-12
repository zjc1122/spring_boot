package cn.zjc.test;


import cn.zjc.aspect.redisdistributedlock.RedisLock;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjiacheng on 2017/11/8.
 */
@Component
public class RedisTest {

    private int i = 500;//假设有500个物品

    @RedisLock(value = "12345")
    public void getGoods(String key, int key1) {
        System.out.println(Thread.currentThread().getName() + "获得了锁");
        i--;
        if(i>0){
            System.out.println(i);
        }else{
            System.out.println("已结束");
        }

    }
}
