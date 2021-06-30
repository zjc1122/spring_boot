package com.zjc.mebatis;

/**
 * @ClassName : ZSqlSession
 * @Author : zhangjiacheng
 * @Date : 2021/1/31
 * @Description : TODO
 */
public class ZSqlSession {

    //配置类
    private ZConfiguration configuration;
    //执行器
    private ZExecutor executor;

    public ZSqlSession(ZConfiguration configuration, ZExecutor executor){
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T> T selectOne(String statementId, Object parameter){
        String sql = ZConfiguration.sqlMappings.getString(statementId);
        return executor.query(sql,parameter);
    }

    /**
     * 获取一个代理对象
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class clazz){
        return configuration.getMapper(clazz,this);
    }
}
