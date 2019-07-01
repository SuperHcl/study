package com.hcl.mybatis.config;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.type.TypeAliasRegistry;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Hucl
 * @date: 2019/7/1 09:25
 * @description: 全局配置类
 */
public class Configuration {
    private DataSource dataSource;

    protected Map<String, MappedStatement> mappedStatementMap = new StrictMap<MappedStatement>("Mapped Statements collection");
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    public Configuration(){}


    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    protected static class StrictMap<V> extends HashMap<String, V> {
        private final String name;

        public StrictMap(String name) {
            super();
            this.name = name;
        }
    }


}