package cn.zjc.mapper.sysrole;


import cn.zjc.mapper.BaseMapper;
import cn.zjc.model.sysrole.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> findRoleByUserId(@Param("userId") Long userId);

}