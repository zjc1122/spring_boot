package cn.zjc.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : zhangjiacheng
 * @ClassName : RedissonConfig
 * @date : 2018/7/9
 * @Description : Redisson配置
 */
@Configuration
public class RedissonConfig {

    @Value("${redis.master.host}")
    private String host;
    @Value("${redis.master.port}")
    private String port;
    @Value("${redis.master.password}")
    private String password;
    @Value("${redis.master.timeout}")
    private int timeOut;
    @Value("${redis.pool.maxTotal}")
    private int maxTotal;
    @Value("${redis.pool.minIdle}")
    private int minIdle;

    @Bean
    public RedissonClient redissonSingle() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config
                .useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setTimeout(timeOut)
                .setConnectionPoolSize(maxTotal)
                .setConnectionMinimumIdleSize(minIdle);
        if (StringUtils.isNotBlank(password)) {
            singleServerConfig.setPassword(password);
        }
        //添加主从配置
//        config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"",""});
        return Redisson.create(config);
    }
}