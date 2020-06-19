package cn.zjc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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

    @Bean(name = "redisConnectionFactory")
    public RedisConnectionFactory factory() {
        return generateConnectionFactory();
    }

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
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
        rcm.setDefaultExpiration(5 * 60);
        return rcm;
    }

    /**
     * Redis 连接
     */
    private RedisConnectionFactory generateConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(masterHost);
        factory.setPort(masterPort);
        factory.setUsePool(true);
        factory.setConvertPipelineAndTxResults(true);
        JedisPoolConfig poolConfig = generatePoolConfig();
        factory.setPoolConfig(poolConfig);
        factory.afterPropertiesSet();
        factory.setTimeout(timeOut);
        return factory;
    }

    private JedisPoolConfig generatePoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        // 半个小时后清除掉多余的空闲链接  避免redis服务器连接数过多挂掉
        poolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        // 每次清除的空闲连接数
        poolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        poolConfig.setTestWhileIdle(testWhileIdle);
        poolConfig.setTestOnBorrow(testOnBorrow);
        poolConfig.setTestOnReturn(testOnReturn);
        // 连接耗尽时是否阻塞, false报异常,true阻塞直到超时, 默认true
        poolConfig.setBlockWhenExhausted(true);
        // 是否开启jmx监控, 默认true
        poolConfig.setJmxEnabled(true);
        return poolConfig;
    }

    /**
     * RedisTemplate配置
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(
            RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        stringRedisTemplate.setEnableTransactionSupport(false);
        stringRedisTemplate.setKeySerializer(stringRedisSerializer);
        stringRedisTemplate.setHashKeySerializer(stringRedisSerializer);
        stringRedisTemplate.setValueSerializer(stringRedisSerializer);
        stringRedisTemplate.setDefaultSerializer(stringRedisSerializer);
        stringRedisTemplate.setConnectionFactory(factory);
        return stringRedisTemplate;
    }

    @Bean
    public JedisPool redisPoolFactory() {
        return new JedisPool(generatePoolConfig(), masterHost, masterPort);
    }

}
