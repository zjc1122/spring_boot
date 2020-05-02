package cn.zjc.enums;

/**
 * Created by zhangjiacheng on 2017/12/14.
 */
public enum DataBaseTypeEnum {

    Default_DB("defaultDataSource"),
    Slave_DB("slaveDataSource");
    private String dbName;

    DataBaseTypeEnum(String dbName) {
        this.dbName = dbName;
    }
    public String get(){
        return dbName;
    }
}
