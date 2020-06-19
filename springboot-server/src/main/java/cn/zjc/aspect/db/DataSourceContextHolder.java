package cn.zjc.aspect.db;


import cn.zjc.enums.DataBaseTypeEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * @author : zhangjiacheng
 * @ClassName : DataSourceContextHolder
 * @date : 2018/6/11
 * @Description : 获得和设置上下文环境 主要负责改变上下文数据源的名称
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();
    private static String masterDBName = DataBaseTypeEnum.MASTER_DB.get();
    private static String slaveDBName = DataBaseTypeEnum.SLAVE_DB.get();

    static void setDbName(String dbType) {
        CONTEXT_HOLDER.set(dbType);
    }

    static String getDbType() {

        String db = CONTEXT_HOLDER.get();
        if (StringUtils.isNotBlank(db)) {
            if (StringUtils.equalsIgnoreCase(slaveDBName, db)) {
                return slaveDBName;
            } else {
                return masterDBName;
            }
        } else {
            return masterDBName;
        }
    }

    static void clearDbType() {
        CONTEXT_HOLDER.remove();
    }
}