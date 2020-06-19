package cn.zjc.enums;

/**
 * Created by zhangjiacheng on 2017/12/14.
 * 数据源配置
 */
public enum DataBaseTypeEnum {

    //主库数据源
    MASTER_DB("defaultDataSource"),
    //从库数据源
    SLAVE_DB("slaveDataSource");

    private String dbName;

    DataBaseTypeEnum(String dbName) {
        this.dbName = dbName;
    }
    public String get(){
        return dbName;
    }
}
