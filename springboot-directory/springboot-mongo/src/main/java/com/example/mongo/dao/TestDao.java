package com.example.mongo.dao;

import com.example.mongo.entity.Study;
import com.example.mongo.entity.User;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/10/15 17:27
 * @description:
 */
public interface TestDao {

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    <T> List<T> find(Query query, Class<T> clazz);

    /**
     * 根据条件查询一个
     * @param query
     * @return
     */
    Study findOne(Query query);

    /**
     * 插入
     * @param entity 修改对象
     */
    void save(User entity);

    /**
     * 更新
     * @param query
     * @param update
     * @return
     */
    UpdateResult update(Query query, Update update);

    /**
     * 查询指定collection中的所有记录
     * @param collectionName collection
     * @return
     */
    List<User> findAll(String collectionName);

    /**
     * 获取总数
     * @param query
     * @param collectionName
     * @return
     */
    long count(Query query, String collectionName);

    /**
     * 根据条件 删除
     * @param query 查询条件
     */
    void remove(Query query);


}
