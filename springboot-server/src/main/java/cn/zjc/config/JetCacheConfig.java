package cn.zjc.config;

import com.alicp.jetcache.CacheBuilder;
import com.alicp.jetcache.anno.CacheConsts;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.alicp.jetcache.anno.support.ConfigProvider;
import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import com.alicp.jetcache.embedded.EmbeddedCacheBuilder;
import com.alicp.jetcache.embedded.LinkedHashMapCacheBuilder;
import com.alicp.jetcache.redis.RedisCacheBuilder;
import com.alicp.jetcache.support.FastjsonKeyConvertor;
import com.alicp.jetcache.support.JavaValueDecoder;
import com.alicp.jetcache.support.JavaValueEncoder;
import com.google.common.collect.Maps;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.util.Pool;

import java.util.Map;

/**
 * @Author : zhangjiacheng
 * @ClassName : JetCacheConfig
 * @Date : 2018/6/13
 * @Description : JetCache基本配置
 */
@Configuration
@EnableMethodCache(basePackages = "cn.zjc")
@EnableCreateCacheAnnotation
public class JetCacheConfig {

    @Value("${redis.master.host}")
    private String host;
    @Value("${redis.master.port}")
    private int port;

    @Bean
    public Pool<Jedis> pool() {
        GenericObjectPoolConfig pc = new GenericObjectPoolConfig();
        pc.setMinIdle(2);
        pc.setMaxIdle(10);
        pc.setMaxTotal(10);
        return new JedisPool(pc, host, port);
    }

    /**
     * JetCache设置配置提供者
     */
    @Bean
    public ConfigProvider configProvider(GlobalCacheConfig globalCacheConfig) {
        ConfigProvider configProvider = new ConfigProvider();
        configProvider.setGlobalCacheConfig(globalCacheConfig);
        return new SpringConfigProvider();
    }

    @Bean
    public GlobalCacheConfig globalCacheConfig(Pool<Jedis> pool) {
        //配置本地缓存
        Map<String, CacheBuilder> localBuilders = Maps.newHashMap();
        EmbeddedCacheBuilder localBuilder = LinkedHashMapCacheBuilder
                .createLinkedHashMapCacheBuilder()
                .keyConvertor(FastjsonKeyConvertor.INSTANCE);
        localBuilders.put(CacheConsts.DEFAULT_AREA, localBuilder);

        //配置Redis二级缓存
        Map<String, CacheBuilder> remoteBuilders = Maps.newHashMap();
        RedisCacheBuilder remoteCacheBuilder = RedisCacheBuilder.createRedisCacheBuilder()
                .keyConvertor(FastjsonKeyConvertor.INSTANCE)
                .valueEncoder(JavaValueEncoder.INSTANCE)
                .valueDecoder(JavaValueDecoder.INSTANCE)
                .jedisPool(pool);
        remoteBuilders.put(CacheConsts.DEFAULT_AREA, remoteCacheBuilder);

        //全局配置
        GlobalCacheConfig globalCacheConfig = new GlobalCacheConfig();
        //设置本地缓存信息
        globalCacheConfig.setLocalCacheBuilders(localBuilders);
        // 设置远程缓存信息
        globalCacheConfig.setRemoteCacheBuilders(remoteBuilders);
        // 设置统计间隔
        globalCacheConfig.setStatIntervalMinutes(15);
        // 禁止启用Area
        globalCacheConfig.setAreaInCacheName(false);

        return globalCacheConfig;
    }
}
