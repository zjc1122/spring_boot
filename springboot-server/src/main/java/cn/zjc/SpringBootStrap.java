package cn.zjc;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.dubbo.qos.common.Constants;
import org.mybatis.spring.annotation.MapperScan;
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
@SpringBootApplication(scanBasePackages = {"cn.zjc"}, exclude = {DataSourceAutoConfiguration.class})
@PropertySource({"classpath:redis.properties", "classpath:zk.properties", "classpath:datasources.properties", "classpath:rabbitmq.properties"})
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@EnableDubbo
@MapperScan("cn.zjc.mapper")
public class SpringBootStrap {

    public static void main(String[] args) {
        //配置dubbo.qos.port端口
        System.setProperty(Constants.QOS_PORT,"33333");
        //配置dubbo.qos.accept.foreign.ip是否关闭远程连接
        System.setProperty(Constants.ACCEPT_FOREIGN_IP,"false");
        SpringApplication.run(SpringBootStrap.class, args);
        //关闭QOS服务
//        Server.getInstance().stop();
    }
}
