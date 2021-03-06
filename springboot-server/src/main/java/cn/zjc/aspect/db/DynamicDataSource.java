package cn.zjc.aspect.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.logging.Logger;

/**
 * @author : zhangjiacheng
 * @ClassName : DynamicDataSource
 * @date : 2018/6/11
 * @Description : 建立动态数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    public Logger getParentLogger() {
        return null;
    }

    @Override
    public Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDbType();
    }

}