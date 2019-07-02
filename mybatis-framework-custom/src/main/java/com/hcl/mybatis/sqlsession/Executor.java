package com.hcl.mybatis.sqlsession;

import com.hcl.mybatis.config.Configuration;
import com.hcl.mybatis.config.MappedStatement;

import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/7/2 17:17
 * @description:
 */
public interface Executor {

    <T> List<T> doQuery(Configuration configuration, MappedStatement mappedStatement, Object param);
}
