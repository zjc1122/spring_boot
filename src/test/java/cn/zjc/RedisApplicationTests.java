package cn.zjc;

import cn.zjc.test.DistributedTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@ComponentScan(basePackages = "cn.zjc")
@PropertySource({"classpath:redis.properties","classpath:zk.properties"})
@EnableAsync
@EnableScheduling
public class RedisApplicationTests {

	@Autowired
	private DistributedTest distributedTest;

	@Test
	public void secKill() throws InterruptedException {
		int k=10;
		for (int i = 0; i < 50; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					distributedTest.getGoods("xxxxx", 111111);
					//System.out.println("我是第" + k + "号线程，我开始获取锁");
				}
			}).start();
			Thread.sleep(2000);

		}

		TimeUnit.SECONDS.sleep(3);
	}

	@Test
	public void sayHello() throws InterruptedException{
		distributedTest.getGoods("xxxxx", 111111);
		TimeUnit.SECONDS.sleep(3);
	}
}
