package cn.zjc.mapper.sysrole;


import cn.zjc.mapper.BaseMapper;
import cn.zjc.model.sysrole.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : zhangjiacheng
 * @ClassName : SysRoleMapper
 * @date : 2018/6/21
 * @Description : 系统用户角色Mapper
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户id查询角色
     *
     * @param userId
     * @return
     */
    List<SysRole> findRoleByUserId(@Param("userId") Long userId);

}