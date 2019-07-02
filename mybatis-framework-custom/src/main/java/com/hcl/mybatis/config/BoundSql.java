package com.hcl.mybatis.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/7/2 14:27
 * @description:
 */
public class BoundSql {
    // 解析出来的sql语句
    private String sql;
    // 解析出来的参数
    private List<ParameterMapping> parameterMappings = new ArrayList<>();

    public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
