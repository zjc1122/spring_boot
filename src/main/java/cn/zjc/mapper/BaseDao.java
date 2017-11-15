package cn.zjc.mapper;

import cn.zjc.model.User;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;

/**
 * Created by zhangjiacheng on 2017/11/15
 */
@Mapper
public interface BaseDao <T>{

    int deleteByPrimaryKey(Serializable  id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Serializable  id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
