package cn.zjc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * @author : zhangjiacheng
 * @ClassName : RedisConfig
 * @date : 2018/6/11
 * @Description : redis配置类
 */
@Configuration
@EnableCaching
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {


    @Value("${redis.master.host}")
    private String masterHost;
    @Value("${redis.master.port}")
    private int masterPort;
    @Value("${redis.master.name}")
    private String masterName;
    @Value("${redis.master.password}")
    private String masterPass;
    @Value("${redis.master.timeout}")
    private int timeOut;
    @Value("${redis.sentinel1.host}")
    private String sentinel1Host;
    @Value("${redis.sentinel1.port}")
    private int sentinel1port;
    @Value("${redis.pool.maxTotal}")
    private int maxTotal;
    @Value("${redis.pool.minIdle}")
    private int minIdle;
    @Value("${redis.pool.maxIdle}")
    private int maxIdle;
    @Value("${redis.pool.maxWaitMillis}")
    private int maxWaitMillis;
    @Value("${redis.pool.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;
    @Value("${redis.pool.numTestsPerEvictionRun}")
    private int numTestsPerEvictionRun;
    @Value("${redis.pool.testWhileIdle}")
    private Boolean testWhileIdle;
    @Value("${redis.pool.testOnBorrow}")
    private Boolean testOnBorrow;
    @Value("${redis.pool.testOnReturn}")
    private Boolean testOnReturn;
    @Value("${redis.pool.nodes}")
    private String nodes;

//    @Bean(name = "redisConnectionFactory")
//    public RedisConnectionFactory factory() {
//        return generateConnectionFactory();
//    }

    /**
     * 生成key的策略
     *
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    /**
     * 管理缓存
     * 1.5.8 版本
     */
//    @Bean
//    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
//        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
//        //设置缓存过期时间
//        rcm.setDefaultExpiration(5 * 60);
//        return rcm;
//    }

    /**
     * Redis 连接
     * 1.5.8配置
     */
//    private RedisConnectionFactory generateConnectionFactory() {
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setHostName(masterHost);
//        factory.setPort(masterPort);
//        factory.setUsePool(true);
//        factory.setConvertPipelineAndTxResults(true);
//        JedisPoolConfig poolConfig = generatePoolConfig();
//        factory.setPoolConfig(poolConfig);
//        factory.afterPropertiesSet();
//        factory.setTimeout(timeOut);
//        return factory;
//    }


    /**
     * 2.3.1 版本
     * @param redisConnectionFactory
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration=RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5));
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }
    /**
     * 2.3.1配置
     * @return
     */
    @Bean
    public RedisConnectionFactory generateConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName(masterHost);
        redisStandaloneConfiguration.setPort(masterPort);
        //获得默认的连接池构造
        //这里需要注意的是，edisConnectionFactoryJ对于Standalone模式的没有（RedisStandaloneConfiguration，JedisPoolConfig）的构造函数，对此
        //我们用JedisClientConfiguration接口的builder方法实例化一个构造器，还得类型转换
        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder();
        JedisClientConfiguration jedisClientConfiguration = builder.usePooling().poolConfig(generatePoolConfig()).build();
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    private JedisPoolConfig generatePoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 半个小时后清除掉多余的空闲链接  避免redis服务器连接数过多挂掉
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        // 每次清除的空闲连接数
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);
        // 连接耗尽时是否阻塞, false报异常,true阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(true);
        // 是否开启jmx监控, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        return jedisPoolConfig;
    }

    /**
     * RedisTemplate配置
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.setConnectionFactory(generateConnectionFactory());
        return redisTemplate;
    }

    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        stringRedisTemplate.setEnableTransactionSupport(false);
        stringRedisTemplate.setKeySerializer(stringRedisSerializer);
        stringRedisTemplate.setHashKeySerializer(stringRedisSerializer);
        stringRedisTemplate.setValueSerializer(stringRedisSerializer);
        stringRedisTemplate.setDefaultSerializer(stringRedisSerializer);
        stringRedisTemplate.setConnectionFactory(generateConnectionFactory());
        return stringRedisTemplate;
    }

    @Bean
    public JedisPool redisPoolFactory() {
        return new JedisPool(generatePoolConfig(), masterHost, masterPort);
    }

}
