package com.hcl.mybatis.sqlsession;

/**
 * @author: Hucl
 * @date: 2019/6/29 16:19
 * @description: SqlSession工厂(工厂模式)
 */
public interface SqlSessionFactory {

    SqlSession openSqlSession();

}
