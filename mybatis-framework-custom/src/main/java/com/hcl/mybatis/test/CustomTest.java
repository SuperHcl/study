package com.hcl.mybatis.test;

import com.hcl.mybatis.sqlsession.SqlSession;
import com.hcl.mybatis.sqlsession.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * @author: Hucl
 * @date: 2019/7/1 15:56
 * @description:
 */
public class CustomTest {

    private SqlSession sqlSession;

    @Before
    public void init() {
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        sqlSession = new SqlSessionFactoryBuilder().build(inputStream).openSqlSession();
    }

    @Test
    public void test01() {
        System.out.println("build sql session");
    }
}
