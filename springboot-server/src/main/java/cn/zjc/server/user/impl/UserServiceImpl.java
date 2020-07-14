package cn.zjc.server.user.impl;

import cn.zjc.mapper.user.UserMapper;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.anno.KeyConvertor;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjc.Entity.user.User;
import com.zjc.Server.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : zhangjiacheng
 * @ClassName : UserServiceImpl
 * @date : 2018/6/20
 * @Description : UserServiceImpl
 */
@Service
@com.alibaba.dubbo.config.annotation.Service(group = "userGroup",version = "1.0.0",timeout = 3000)
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public List<User> selectAll(User user) {
        return userMapper.selectAll(user);
    }

    @Override
    public int selectCount(User user) {
        return userMapper.selectCount(user);
    }

    /**
     * 为方法增加缓存使用注解@Cached，名称为userCache，缓存中对应的key为id的值,缓存失效时间为60秒(单位默认为秒)，可以缓存空值。
     * 刷新缓存使用注解@CacheRefresh，refresh为刷新间隔，30秒刷新一次(单位默认为秒)，指定的key120秒没有访问就停止刷新。
     *
     * @param id
     * @return
     */
    @Override
    @Cached(name = "userCache_", key = "#id", expire = 120, cacheNullValue = true, keyConvertor = KeyConvertor.FASTJSON)
    @CacheRefresh(refresh = 30, stopRefreshAfterLastAccess = 120)
    public User selectByPrimaryKey(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<User> selectPageAll(Integer page, Integer rows, User user) {
        // 设置分页条件
        PageHelper.startPage(page, rows);
        //排序(格式 : 字段 + 顺序)
        PageHelper.orderBy("id desc");
        List<User> list = this.selectAll(user);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(User user) {
        return userMapper.insert(user);
    }

    /**
     * 更新缓存使用注解@CacheUpdate，更新的缓存名称为userCache，缓存中对应的key为user.id的值，更新key的值为value的值。
     *
     * @param user
     * @return
     */
    @Override
    @CacheUpdate(name = "userCache_", key = "#user.id", value = "#user")
    @Transactional(rollbackFor = Exception.class)
    public Integer update(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    /**
     * 删除缓存使用注解@CacheInvalidate，表示从缓存名称为userCache，将对应key为id值的记录从缓存userCache中删除
     *
     * @param id
     * @return
     */
    @Override
    @CacheInvalidate(name = "userCache_", key = "#id")
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }
}
