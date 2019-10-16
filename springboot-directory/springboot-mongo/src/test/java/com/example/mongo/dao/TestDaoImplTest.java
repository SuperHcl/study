package com.example.mongo.dao;

import com.example.mongo.entity.User;
import com.mongodb.client.result.UpdateResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;


/**
 * @author: Hucl
 * @date: 2019/10/16 10:08
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDaoImplTest {

    @Resource
    private TestDao testDao;

    @Test
    public void find() {
        Query query = new Query(Criteria.where("name").is("科比"));
        List<User> users = testDao.find(query, User.class);
        users.forEach(System.out::println);
    }

    @Test
    public void findOne() {
    }

    @Test
    public void save() {

        User user = new User();
        user.setName("科比");
        user.setAge(26);
        testDao.save(user);
        User user2 = new User();
        user2.setName("詹姆斯");
        user2.setAge(33);
        testDao.save(user2);
        User user3 = new User();
        user3.setName("韦德");
        user3.setAge(36);
        testDao.save(user3);
        User user4 = new User();
        user4.setName("罗斯");
        user4.setAge(30);
        testDao.save(user4);
    }

    @Test
    public void update() {

        Query query = new Query(Criteria.where("name").is("韦德"));
        Update update = new Update();
        update.set("age", "46");
        UpdateResult update1 = testDao.update(query, update);
        System.out.println(update1.getUpsertedId());
    }

    @Test
    public void findAll() {
        List<User> user = testDao.findAll("user");
        user.forEach(System.out::println);
    }

    @Test
    public void count() {
        Query query = new Query();
        long count = testDao.count(query, "user");
        System.out.println("总条数：" + count);
    }

    @Test
    public void remove() {
        Query query = new Query(Criteria.where("name").is("科比"));
        testDao.remove(query);
    }
}