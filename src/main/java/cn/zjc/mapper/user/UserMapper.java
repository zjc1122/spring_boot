package cn.zjc.mapper.user;

import cn.zjc.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    void insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long  id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAllUser();
}