package com.supergo.service.base.impl;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supergo.service.base.BaseService;
import com.supergo.page.PageResult;
import com.supergo.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

/**
 * 功能描述：增删改查通用service实现类
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 15:31
*/
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    //spring 4.x 版本之后引入的泛型依赖注入
    @Autowired
    private Mapper<T> mapper;

    @Override
    public T findOne(Serializable id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public HttpResult findAll() {
        return HttpResult.ok(mapper.selectAll());
    }

    @Override
    public List<T> findByWhere(T t) {
        return mapper.select(t);
    }

   @Override
    public PageResult findPage(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<T> list = mapper.selectAll();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(),
                pageInfo.getList(),page);
    }

  @Override
    public PageResult findPage(Integer page, Integer rows, T t) {
        PageHelper.startPage(page, rows);
        List<T> list = mapper.select(t);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(),
                pageInfo.getList(),page);
    }

    @Override
    public void add(T t) {
        mapper.insertSelective(t);
    }

    @Override
    public void update(T t) {
        mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public void deleteByIds(Serializable[] ids) {
        if (ids != null && ids.length > 0) {
            for (Serializable id : ids) {
                mapper.deleteByPrimaryKey(id);
            }
        }
    }


}