package com.zjc.designmode.template.jdbc;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

/**
 * @ClassName : MemberDao
 * @Author : zhangjiacheng
 * @Date : 2020/12/22
 * @Description : TODO
 */
public class MemberDao extends JdbcTemplate {

    public MemberDao(DataSource dataSource) {
        super(dataSource);
    }



    public List<?> selectAll(){
        String sql = "select * from t_member";
        return super.executeQuery(sql, new RomMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws Exception {
                Member member = new Member();
                member.setUsername(rs.getString("username"));
                member.setPassword(rs.getString("password"));
                member.setAddr(rs.getString("addr"));
                member.setAge(rs.getInt("age"));
                return member;
            }
        },null);
    }
}
