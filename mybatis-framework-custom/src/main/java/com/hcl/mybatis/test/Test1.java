package com.hcl.mybatis.test;

import com.hcl.mybatis.dao.UserDao;
import com.hcl.mybatis.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: Hucl
 * @date: 2019/6/29 16:44
 * @description:
 */
public class Test1 {
    private SqlSession sqlSession;
    @Before
    public void init() throws IOException {
        String resource = "mybatis/SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSession = new SqlSessionFactoryBuilder().build(inputStream).openSession();
    }

    @Test
    public void test1() {
        // selectOne(String statementId, Object o)
        // statementId = mapper文件的 namespace + 语句的id
        User user = sqlSession.selectOne("com.hcl.mybatis.dao.UserDao.findUserById", 2);
        System.out.println(user.toString());
    }


    @Test
    public void test2() {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.findUserById(1);
        System.out.println(user.toString());
    }


}
