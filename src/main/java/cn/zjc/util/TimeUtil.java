package cn.zjc.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    /**
     * 格式化时间
     * @param date
     * @return
     */
    public static String localDateTimetoString(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }
}
