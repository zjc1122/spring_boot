package cn.zjc.mapper.user;

import cn.zjc.mapper.BaseMapper;
import cn.zjc.model.user.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : zhangjiacheng
 * @ClassName : UserMapper
 * @date : 2018/6/21
 * @Description : 用户Mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}