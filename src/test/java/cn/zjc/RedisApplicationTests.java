package cn.zjc;

import cn.zjc.test.RedisTest;
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
@PropertySource({"classpath:redis.properties"})
@EnableAsync
@EnableScheduling
public class RedisApplicationTests {

	@Autowired
	private RedisTest redisTest;

	@Test
	public void secKill() throws InterruptedException {
		for (int i = 0; i < 510; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					redisTest.getGoods("xxxxx", 111111);
				}
			}).start();
		}

		TimeUnit.SECONDS.sleep(10);
	}

	@Test
	public void sayHello() throws InterruptedException{
		redisTest.getGoods("xxxxx", 111111);
		TimeUnit.SECONDS.sleep(3);
	}
}
