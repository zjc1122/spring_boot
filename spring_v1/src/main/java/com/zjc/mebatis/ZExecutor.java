package com.zjc.mebatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ClassName : ZExecutor
 * @Author : zhangjiacheng
 * @Date : 2021/1/31
 * @Description : TODO
 */
public class ZExecutor {


    public <T> T query(String sql, Object parameter) {

        Connection conn = null;
        Statement stmt = null;
        User user = new User();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zjc?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull","root","root");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(sql,parameter));
            while (rs.next()){
                int uid = rs.getInt("user_id");
                String userName = rs.getString("user_name");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                user.setUserId(uid);
                user.setUserName(userName);
                user.setPassword(password);
                user.setPhone(phone);
            }
            rs.close();
            stmt.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (stmt!=null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return (T)user;
    }

}
