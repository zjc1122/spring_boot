package cn.zjc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring boot 启动类
 *
 * @author zhangjiacheng
 */
@SpringBootApplication(scanBasePackages = "cn.zjc",exclude = {DataSourceAutoConfiguration.class})
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
//@ComponentScan(basePackages = "cn.zjc")
@PropertySource({"classpath:redis.properties", "classpath:zk.properties", "classpath:datasources.properties", "classpath:rabbitmq.properties"})
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
public class SpringBootStrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStrap.class, args);
    }
}
