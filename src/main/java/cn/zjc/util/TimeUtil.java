package cn.zjc.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @ClassName : TimeUtil
 * @Author : zhangjiacheng
 * @Date : 2018/6/10
 * @Description : 时间工具类
 */
public class TimeUtil {


    public final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public final static DateTimeFormatter formatter_2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * Date转换为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定日期的秒
     *
     * @param localDateTime
     * @return
     */
    public static Long getSecondsByTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 获取指定日期的毫秒
     *
     * @param localDateTime
     * @return
     */
    public static Long getMilliByTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取一天的开始时间，2018-06-10T00:00
     *
     * @param localDateTime
     * @return
     */
    public static LocalDateTime getDayStart(LocalDateTime localDateTime) {
        return localDateTime.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    /**
     * 获取一天的结束时间，2018-06-10T23:59:59.999
     *
     * @param localDateTime
     * @return
     */
    public static LocalDateTime getDayEnd(LocalDateTime localDateTime) {
        return localDateTime.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999);
    }

    /**
     * 返回日期格式(yyyy-MM-dd HH:mm:ss)
     *
     * @param localDateTime
     * @return
     */
    public static String formatTime(LocalDateTime localDateTime) {
        return formatter.format(localDateTime);
    }

    /**
     * 返回日期格式(yyyyMMddHHmmss)
     *
     * @param localDateTime
     * @return
     */
    public static String formatTime2(LocalDateTime localDateTime) {
        return formatter_2.format(localDateTime);
    }

    /**
     * 获取当前日期格式(yyyy-MM-dd HH:mm:ss)
     *
     * @return
     */
    public static String getCurrDateTime() {
        return formatter.format(LocalDateTime.now());
    }

    /**
     * 获取当前日期格式(yyyyMMddHHmmss)
     *
     * @return
     */
    public static String getCurrDateTime2() {
        return formatter_2.format(LocalDateTime.now());
    }

    public static void main(String[] args) {
        TimeUtil.getDayStart(LocalDateTime.now());
        TimeUtil.getDayEnd(LocalDateTime.now());
        TimeUtil.localDateTimeToDate(LocalDateTime.now());
        System.out.println(TimeUtil.getDayStart(LocalDateTime.now()));
        System.out.println(TimeUtil.getDayEnd(LocalDateTime.now()));
        System.out.println(TimeUtil.localDateTimeToDate(LocalDateTime.now()));
    }
}
