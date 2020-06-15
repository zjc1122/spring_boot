package cn.zjc.server.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @ClassName : JedisService
 * @Author : zhangjiacheng
 * @Date : 2020/6/12
 * @Description : redis操作
 */
@Service
public class JedisService {
    private static final Logger logger = LoggerFactory.getLogger(JedisService.class);
    @Resource
    private JedisPool jedisPool;

    //key不存在时设置value
    private static final String NX = "NX";
    //key存在时设置value
    private static final String XX = "XX";
    //设置失效时长，单位秒
    private static final String EX = "EX";
    //设置失效时长，单位毫秒
    private static final String PX = "PX";

    /**
     * 判断key是否存在，不在则赋值
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setNX(String key, String value, long time) {
        boolean res = false;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String set = jedis.set(key, value, NX, PX, time);
            if (StringUtils.isBlank(set)) {
                res = true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return res;

    }

    /**
     * 返回一个字符串，也就是键的旧值,并设置新的值。 如果键不存在，则返回nil
     *
     * @param key
     * @param value
     */
    public long getAndSet(String key, String value) {
        Jedis jedis = null;
        String set;
        try {
            jedis = jedisPool.getResource();
            set = jedis.getSet(key, value);
            if (StringUtils.isBlank(set)) {
                return 0;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0;
        } finally {
            returnResource(jedis);
        }
        return Long.parseLong(set);
    }

    public long get(String key) {
        Jedis jedis = null;
        String value;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
            if (StringUtils.isBlank(value)) {
                return 0;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0;
        } finally {
            returnResource(jedis);
        }
        return Long.parseLong(value);
    }

    public void delete(final String key) {
        if (exists(key)) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                jedis.del(key);
            } catch (Exception e) {
                logger.error(e.getMessage());
            } finally {
                returnResource(jedis);
            }

        }
    }

    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 返还到连接池
     *
     * @param jedis
     *
     */
    private void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}
