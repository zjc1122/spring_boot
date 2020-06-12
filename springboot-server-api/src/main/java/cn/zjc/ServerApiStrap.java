package cn.zjc;

import org.springframework.boot.SpringApplication;

/**
 * Spring boot 启动类
 *
 * @author zhangjiacheng
 */
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ServerApiStrap {

    public static void main(String[] args) {
        SpringApplication.run(ServerApiStrap.class, args);
    }
}
