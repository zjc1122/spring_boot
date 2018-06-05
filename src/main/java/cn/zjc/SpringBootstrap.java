package cn.zjc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring boot 启动类
 *
 * @author zhangjiacheng
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("cn.zjc.mapper")
@ComponentScan(basePackages = "cn.zjc")
@PropertySource({"classpath:redis.properties", "classpath:zk.properties", "classpath:datasources.properties", "classpath:rabbitmq.properties"})
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
public class SpringBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootstrap.class, args);
    }
}
