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
public class ApplicationTests {

	@Autowired
	private DistributedTest distributedTest;

	@Test
	public void secKill() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						distributedTest.getGoods("123456", 111111);
						Thread.sleep(10000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			Thread.sleep(1000);
		}
		TimeUnit.SECONDS.sleep(3);
	}

}
