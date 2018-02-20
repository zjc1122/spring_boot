package cn.zjc.mapper.sysuser;


import cn.zjc.mapper.BaseMapper;
import cn.zjc.model.sysuser.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser selectSysUserByUsername(String username);
}