package com.hcl.mybatis.config;

import com.hcl.mybatis.util.GenericTokenParser;
import com.hcl.mybatis.util.ParameterMappingTokenHandler;

/**
 * @author: Hucl
 * @date: 2019/7/2 14:26
 * @description:
 */
public class SqlSource {

    private String sqlText;

    public SqlSource(String sqlText) {
        this.sqlText = sqlText;
    }

    public BoundSql getBoundSql() {
        // 解析sql文本
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", tokenHandler);
        String sql = genericTokenParser.parse(sqlText);
        // 将解析出来的sql语句和解析出来的参数使用 组合模式 绑定到一个类中
        return new BoundSql(sql, tokenHandler.getParameterMappings());
    }
}
