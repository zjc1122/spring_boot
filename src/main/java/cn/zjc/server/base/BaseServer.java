package cn.zjc.server.base;

import com.github.pagehelper.PageInfo;
import java.util.List;

/**
 * Created by zhangjiacheng on 2017/11/17.
 */
public interface BaseServer<T> {
    /**
     * 查询所有数据
     * @return
     */
    public List<T> selectAll();

    /**
     * 根据条件查询数据
     * @return
     */
    public List<T> selectAll(T record);
    /**
     * 查询数据的条数
     * @param record
     * @return
     */
    public int selectCount(T record);
    /**
     * 分页查询
     * @param page
     * @param rows
     * @param record
     * @return
     */
    public PageInfo<T> selectPageAll(Integer page, Integer rows, T record);
    /**
     * 新增数据，返回成功的条数
     *
     * @param record
     * @return
     */
    public Integer save(T record);
    /**
     * 新增数据，使用不为null的字段，返回成功的条数
     *
     * @param record
     * @return
     */
    public Integer saveSelective(T record);

    /**
     * 修改数据，返回成功的条数
     *
     * @param record
     * @return
     */
    public Integer update(T record);

    /**
     * 修改数据，使用不为null的字段，返回成功的条数
     *
     * @param record
     * @return
     */
    public Integer updateSelective(T record);

    /**
     * 根据id删除数据
     *
     * @param id
     * @return
     */
    public Integer deleteById(Long id);

    /**
     * 根据条件做删除
     *
     * @param record
     * @return
     */
    public Integer deleteByWhere(T record);
}
