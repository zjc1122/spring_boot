package com.zjc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot 启动类
 *
 * @author zhangjiacheng
 */
@SpringBootApplication(scanBasePackages = "com.zjc")
@MapperScan("com.zjc")
public class OauthResourceStrap {

    public static void main(String[] args) {
        SpringApplication.run(OauthResourceStrap.class, args);
    }
}
