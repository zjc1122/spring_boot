package com.zjc.mapper;

import com.zjc.domain.SysRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper{

    @Select("SELECT r.* FROM sys_role r, sys_user_roles ur " +
            "WHERE r.id=ur.roles_id AND ur.sys_user_id=#{uid}")
    List<SysRole> findByUid(long uid);
}
