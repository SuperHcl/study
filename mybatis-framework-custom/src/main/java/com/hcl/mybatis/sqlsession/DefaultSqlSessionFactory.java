package com.hcl.mybatis.sqlsession;


import com.hcl.mybatis.config.Configuration;

/**
 * @author: Hucl
 * @date: 2019/6/29 16:18
 * @description:
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSqlSession() {
        return new DefaultSqlSession(configuration);
    }
}
