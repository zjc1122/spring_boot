package cn.zjc.aspect.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.logging.Logger;

/**
 * Created by zhangjiacheng on 2017/12/13.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

     @Override
     public Logger getParentLogger() {
            return null;
     }

     @Override
     protected Object determineCurrentLookupKey() {
            return DataSourceContextHolder. getDbType();
     }

}