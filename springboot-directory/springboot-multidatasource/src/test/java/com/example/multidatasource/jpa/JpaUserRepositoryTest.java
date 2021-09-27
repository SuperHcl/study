package com.example.multidatasource.jpa;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hucl
 * @date 2021/9/22 5:26 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JpaUserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {
        // 创建5条记录
        userRepository.save(new User("AAA", 10));
        userRepository.save(new User("BBB", 20));
        userRepository.save(new User("CCC", 30));
        userRepository.save(new User("DDD", 40));
        userRepository.save(new User("EEE", 50));
    }

    @Test
    public void test() throws Exception {

        // 测试findAll, 查询所有记录
        Assert.assertEquals(5, userRepository.findAll().size());

        // 测试findByName, 查询姓名为FFF的User
        Assert.assertEquals(50, userRepository.findByName("EEE").getAge().longValue());

        // 测试findUser, 查询姓名为FFF的User
        Assert.assertEquals(50, userRepository.findUser("EEE").getAge().longValue());

        // 测试findByNameAndAge, 查询姓名为FFF并且年龄为60的User
        Assert.assertEquals("EEE", userRepository.findByNameAndAge("EEE", 50).getName());

        // 测试findAll, 查询所有记录, 验证上面的删除是否成功
        Assert.assertEquals(4, userRepository.findAll().size());

    }

    @Test
    public void delete() {
        // 测试删除姓名为AAA的User
        userRepository.delete(userRepository.findByName("AAA"));
    }
}