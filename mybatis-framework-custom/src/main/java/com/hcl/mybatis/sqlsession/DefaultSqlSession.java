package com.hcl.mybatis.sqlsession;

import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/6/29 16:18
 * @description:
 */
public class DefaultSqlSession implements SqlSession {

    public <T> T selectOne(String statementId, Object obj) {
        return null;
    }

    public <T> List<T> selectList(String statementId, Object obj) {
        return null;
    }
}
