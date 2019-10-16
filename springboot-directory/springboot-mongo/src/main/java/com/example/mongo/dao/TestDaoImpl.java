package com.example.mongo.dao;

import com.example.mongo.entity.Study;
import com.example.mongo.entity.User;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/10/16 09:27
 * @description:
 */
@Repository
public class TestDaoImpl implements TestDao {

    @Resource
    private MongoTemplate template;


    @Override
    public <T> List<T> find(Query query, Class<T> clazz) {
        return template.find(query, clazz);
    }

    @Override
    public Study findOne(Query query) {
        return template.findOne(query, Study.class);
    }

    @Override
    public void save(User entity) {
        template.save(entity);
    }

    @Override
    public UpdateResult update(Query query, Update update) {
        return template.updateMulti(query, update, User.class);
    }

    @Override
    public List<User> findAll(String collectionName) {
        return template.findAll(User.class, collectionName);
    }

    @Override
    public long count(Query query, String collectionName) {
        return template.count(query, collectionName);
    }

    @Override
    public void remove(Query query) {
        template.remove(query, "user");
    }
}
