package cn.zjc.mapper.sysuser;


import cn.zjc.aspect.db.TargetDataSource;
import cn.zjc.enums.DataBaseTypeEnum;
import cn.zjc.mapper.BaseMapper;
import cn.zjc.model.sysuser.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author : zhangjiacheng
 * @ClassName : SysUserMapper
 * @date : 2018/6/21
 * @Description : 系统用户Mapper
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户名查询系统用户
     *
     * @param username
     * @return
     */
    @TargetDataSource(DataBaseTypeEnum.MASTER_DB)
    SysUser selectSysUserByUsername(@Param("username") String username);
}