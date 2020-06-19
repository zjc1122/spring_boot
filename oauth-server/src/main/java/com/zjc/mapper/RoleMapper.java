package com.zjc.mapper;

import com.zjc.domain.SysRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper{

    @Select("SELECT r.id, r.role_name roleName, r.role_desc roleDesc " +
            "FROM sys_role r, sys_user_roles ur " +
            "WHERE r.id=ur.sys_user_id AND ur.uid=#{uid}")
    List<SysRole> findByUid(Integer uid);
}
