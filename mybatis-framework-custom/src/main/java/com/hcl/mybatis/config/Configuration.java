package com.hcl.mybatis.config;


import com.hcl.mybatis.type.TypeAliasRegistry;

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

    protected Map<String, MappedStatement> mappedStatementMap = new StrictMap<>("Mapped Statements collection");
    private final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    public Configuration(){}


    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }

    public void addMappedStatement(String statementId, MappedStatement mappedStatement) {
        this.mappedStatementMap.put(statementId, mappedStatement);
    }

    protected static class StrictMap<V> extends HashMap<String, V> {
        private final String name;

        public StrictMap(String name) {
            super();
            this.name = name;
        }
    }


}