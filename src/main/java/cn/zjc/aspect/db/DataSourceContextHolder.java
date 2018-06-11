package cn.zjc.aspect.db;


import cn.zjc.enums.DataBaseType;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName : DataSourceContextHolder
 * @author : zhangjiacheng
 * @date : 2018/6/11
 * @Description : 获得和设置上下文环境 主要负责改变上下文数据源的名称
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    private static String defaultDBName = DataBaseType.Default_DB.get();
    private static String slaveDBName = DataBaseType.Slave_DB.get();

    public static void setDbName(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDbType() {

        String db = contextHolder.get();
        if (StringUtils.isNotBlank(db)) {
            if (StringUtils.equalsIgnoreCase(slaveDBName, db)) {
                return slaveDBName;
            } else {
                return defaultDBName;
            }
        } else {
            return defaultDBName;
        }
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}