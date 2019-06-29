package com.hcl.mybatis.sqlsession;

import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/6/29 16:16
 * @description: （CRUD的方法）
 */
public interface SqlSession {

    <T> T selectOne(String statementId, Object obj);

    <T> List<T> selectList(String statementId, Object obj);
}
