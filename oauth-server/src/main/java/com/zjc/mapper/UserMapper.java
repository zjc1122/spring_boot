package com.zjc.mapper;

import com.zjc.domain.SysUser;
import org.apache.ibatis.annotations.Select;

public interface UserMapper{

    @Select("select * from sys_user where user_name = #{username}")
//    @Results({
//            @Result(id = true, property = "id", column = "id"),
//            @Result(property = "roles", column = "id", javaType = List.class,
//                many = @Many(select = "com.zjc.mapper.RoleMapper.findByUid"))
//    })
    SysUser findByName(String username);

}
