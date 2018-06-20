package cn.zjc.server.user;

import cn.zjc.model.user.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author : zhangjiacheng
 * @ClassName : UserService
 * @date : 2018/6/20
 * @Description : UserService
 */
public interface UserService {

    /**
     * 查询所有数据
     *
     * @return
     */
    List<User> selectAll();

    /**
     * 根据条件查询数据
     *
     * @return
     */
    List<User> selectAll(User record);

    /**
     * 查询数据的条数
     *
     * @param record
     * @return
     */
    int selectCount(User record);

    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    User selectByPrimaryKey(Long id);

    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @param record
     * @return
     */
    PageInfo<User> selectPageAll(Integer page, Integer rows, User record);

    /**
     * 新增数据，返回成功的条数
     *
     * @param record
     * @return
     */
    Integer save(User record);

    /**
     * 修改数据，返回成功的条数
     *
     * @param record
     * @return
     */
    Integer update(User record);

    /**
     * 根据id删除数据
     *
     * @param id
     * @return
     */
    Integer deleteById(Long id);

}
