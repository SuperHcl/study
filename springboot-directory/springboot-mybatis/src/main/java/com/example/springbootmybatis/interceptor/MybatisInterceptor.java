package com.example.springbootmybatis.interceptor;

import com.example.springbootmybatis.entity.ColMapping;
import com.example.springbootmybatis.entity.NormalSqlStructure;
import com.example.springbootmybatis.util.SqlParserUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


/**
 * mybatis拦截器
 *
 * @author Hu.ChangLiang
 * @date 2023/5/30 11:22
 */
@Intercepts(@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class MybatisInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(MybatisInterceptor.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];

        BoundSql boundSql = mappedStatement.getBoundSql(args[1]);

        String originalSql = boundSql.getSql();
        log.info("原始sql语句：{}", originalSql);

        List<String> tables = SqlParserUtil.getSelectTables(originalSql);
        log.info("sql语句中的操作的表: {}", tables);

        List<String> items = SqlParserUtil.getSelectColumns(originalSql);
        log.info("sql语句中操作的列：{}", items);

        if (SqlCommandType.SELECT == mappedStatement.getSqlCommandType()) {
            // select操作， 修改结果集

        } else if (SqlCommandType.DELETE == mappedStatement.getSqlCommandType()) {
            // ignore
        } else {
            // update or insert操作, 修改入参
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject instanceof Map) {
                Map<String, Object> parameter = (Map<String, Object>) parameterObject;
                String name = (String) parameter.getOrDefault("name", "");

            } else {

            }

        }

        NormalSqlStructure structure = SqlParserUtil.getSelectStructure(originalSql, true);
        if (structure.getColMappings() != null) {
            for (ColMapping colMapping : structure.getColMappings()) {
                boolean b = colMapping.getName().equalsIgnoreCase("");
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }


}
