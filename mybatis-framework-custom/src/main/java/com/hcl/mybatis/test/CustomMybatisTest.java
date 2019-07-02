package com.hcl.mybatis.test;

import com.hcl.mybatis.pojo.User;
import com.hcl.mybatis.sqlsession.SqlSession;
import com.hcl.mybatis.sqlsession.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * @author: Hucl
 * @date: 2019/6/29 16:21
 * @description:
 */
public class CustomMybatisTest {
    private SqlSession sqlSession;

    @Before
    public void init() {
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        sqlSession = new SqlSessionFactoryBuilder().build(inputStream).openSqlSession();
    }

    @Test
    public void test01() {
        User user = sqlSession.selectOne("test.findUserById", 1);
        System.out.println(user);
    }

    @Test
    public void test02() {
        User user = sqlSession.selectOne("test.findUserByName", "李四");
        System.out.println(user);
    }

    @Test
    public void test03() {
        User user = new User();
        user.setAge(12);
        User result = sqlSession.selectOne("test.findUserByObj", user);
        System.out.println(result);
    }
}
