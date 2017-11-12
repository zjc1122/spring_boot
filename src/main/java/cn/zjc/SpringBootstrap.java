package cn.zjc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring boot 启动类
 * @author zhangjiacheng
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "cn.zjc")
@PropertySource({"classpath:redis.properties","classpath:zk.properties"})
@EnableAsync
@EnableScheduling
public class SpringBootstrap {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootstrap.class, args);
	}
}
