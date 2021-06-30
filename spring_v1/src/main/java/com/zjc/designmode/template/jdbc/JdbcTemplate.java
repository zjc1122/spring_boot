package com.zjc.designmode.template.jdbc;

import com.google.common.collect.Lists;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName : JdbcTemplate
 * @Author : zhangjiacheng
 * @Date : 2020/12/22
 * @Description : TODO
 */
public abstract class JdbcTemplate {

    private DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<?> executeQuery(String sql, RomMapper<?> romMapper, Object[] values){

        try {
            //1.获取连接
            Connection connection = this.getConnection();
            //2.创建语句集
            PreparedStatement statement =  this.createPrepareStatement(connection,sql);
            //3.执行语句集
            ResultSet rs = this.executeQuery(statement,values);
            //4.处理结果集
            List<?> result = this.paresResultSet(rs,romMapper);
            //5.关闭结果集
            this.closeResultSet(rs);
            //6.关闭语句集
            this.closeStatement(statement);
            //7.关闭连接
            this.closeConnection(connection);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    protected void closeConnection(Connection connection) throws Exception {
        connection.close();
    }

    protected void closeStatement(PreparedStatement statement) throws Exception {
        statement.close();
    }

    protected void closeResultSet(ResultSet rs) throws Exception {
        rs.close();
    }


    protected List<?> paresResultSet(ResultSet rs, RomMapper<?> romMapper) throws Exception {
        List<Object> result = Lists.newArrayList();
        int rowNum = 1;
        while (rs.next()){
            result.add(romMapper.mapRow(rs,rowNum ++));
        }
        return result;
    }

    protected ResultSet executeQuery(PreparedStatement statement, Object[] values) throws SQLException {
        for (int i = 0;i<values.length;i++){
            statement.setObject(i,values[i]);
        }
        return statement.executeQuery();
    }

    protected PreparedStatement createPrepareStatement(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }


}
