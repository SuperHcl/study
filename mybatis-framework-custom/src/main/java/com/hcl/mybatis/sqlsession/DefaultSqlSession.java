package com.hcl.mybatis.sqlsession;

import com.hcl.mybatis.config.Configuration;
import com.hcl.mybatis.config.MappedStatement;

import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/7/2 17:14
 * @description:
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statementId, Object obj) {
        List<Object> list = selectList(statementId, obj);
        if (list!=null && list.size() == 1) {
            return (T) list.get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statementId, Object obj) {
        // 真正和数据库进行CRUD操作的类
        // 执行statement获取MappedStatement对象
        Executor executor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return executor.doQuery(configuration, mappedStatement, obj);
    }
}
