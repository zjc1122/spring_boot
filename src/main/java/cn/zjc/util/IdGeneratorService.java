package cn.zjc.util;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author yangbo
 * @date 2018/7/3
 */
@Slf4j
@Service
public class IdGeneratorService {

    /**
     * 支付宝订单尾号默认长度
     */
    private static final long ALIPAY_COUNT = 11;

    /**
     * 天猫订单尾号默认长度
     */
    private static final long TMALL_COUNT = 8;

    /**
     * 天猫订单号前缀基准值
     */
    private static final long ORDER_PREFIX = 1000;

    /**
     * 基准日期
     */
    private static final LocalDate BASE_DATE = LocalDate.of(2018, 1, 1);

    @Value("${server.port}")
    private String port;

    @Resource
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedissonClient redisson;

    /**
     * 支付宝订单号生成器
     * <pre>
     *     example:
     *          支付宝流水号:2018062021001001710502897238
     * </pre>
     */
    public String generateAlipayOrderNumber(String businessCode) {
        // 随机生成大于0小于50的订单号偏量值
        int delta = new Random().nextInt(50) + 1;
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 格式化当前时间到毫秒
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        // 格式化当前时间到天
        String keySuffix = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 创建Redis联合键值
        String unionKey = String.join(":", businessCode, "Alipay", keySuffix);
        // 通过RedissonClient获取分布式原子类,生成订单尾号的初始值
        RAtomicLong counter = redisson.getAtomicLong(unionKey);
        // 执行递增操作
        counter.addAndGet(delta);
        // 获取counter的长度
        int length = String.valueOf(counter.get()).length();
        // 如果长度小于COUNT值,需要补位"0"
        StringBuilder sb = new StringBuilder();
        if (length < ALIPAY_COUNT) {
            sb.append(timestamp);
            for (int i = 0; i < ALIPAY_COUNT - length; i++) {
                sb.append("0");
            }
            sb.append(counter.get());
        } else {
            sb.append(timestamp).append(counter.get());
        }
        // 历史Redis联合键值最多在缓存中保留24小时
        redisTemplate.expire(unionKey, 24, TimeUnit.HOURS);
        return sb.toString();
    }

    /**
     * 天猫订单号生成器
     * <pre>
     *     example:
     *          天猫订单号:165035137214303944
     * </pre>
     */
    public String generateTMallOrderNumber(String businessCode) {
        // 随机生成大于0小于50的订单号偏量值
        int delta = new Random().nextInt(50) + 1;
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 获取当前日期
        LocalDate today = now.toLocalDate();
        // 与基准日期相差的天数
        long days = today.toEpochDay() - BASE_DATE.toEpochDay();
        // 订单号前缀
        String prefix = String.valueOf(ORDER_PREFIX + days);
        // 格式化当前时间为时分秒
        String middle = now.format(DateTimeFormatter.ofPattern("HHmmss"));
        // 格式化当前时间到天
        String keySuffix = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 创建Redis联合键值
        String unionKey = String.join(":", businessCode, "TMall", keySuffix);
        // 通过RedissonClient获取分布式原子类,生成订单尾号的初始值
        RAtomicLong counter = redisson.getAtomicLong(unionKey);
        // 执行递增操作
        counter.addAndGet(delta);
        // 获取counter的长度
        int length = String.valueOf(counter.get()).length();
        // 如果长度小于COUNT值,需要补位"0"
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append(middle);
        if (length < TMALL_COUNT) {
            for (int i = 0; i < TMALL_COUNT - length; i++) {
                sb.append("0");
            }
            sb.append(counter.get());
        } else {
            sb.append(counter.get());
        }
        // 历史Redis联合键值最多在缓存中保留24小时
        redisTemplate.expire(unionKey, 24, TimeUnit.HOURS);
        return sb.toString();
    }

}
