package com.example.springbootmybatis.interceptor;

import com.example.springbootmybatis.entity.ColMapping;
import com.example.springbootmybatis.entity.NormalSqlStructure;
import com.example.springbootmybatis.handler.DecryptionResultHandler;
import com.example.springbootmybatis.handler.EncryptionParamHandler;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * 解密拦截器
 *
 * @author Hu.ChangLiang
 * @date 2023/5/31 15:45
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class DecryptionInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(DecryptionInterceptor.class);

    private static final String TARGET_TABLE = "student";
    private static final String TARGET_COLUMN = "name";
    private static final List<String> TARGET_FIELDS = Arrays.asList("name");

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object parameter = args[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String originalSql = boundSql.getSql();
        log.info("拦截的方法名是：{}, 原始sql语句：{}", mappedStatement.getId(), originalSql);


        // 判断是否为 SELECT 语句
        if (SqlCommandType.SELECT == mappedStatement.getSqlCommandType()) {
            // 先执行，让查询出结果集
            Object proceed = invocation.proceed();
            // 解析SQL为标准化对象
            NormalSqlStructure sqlStructure = SqlParserUtil.getSelectStructure(originalSql, true);

            if (!containsTable(sqlStructure.getTableNames(), TARGET_TABLE) ||
                    !containsColumn(sqlStructure, TARGET_TABLE, TARGET_COLUMN)) {
                return proceed;
            }

            // 进行解密处理
            DecryptionResultHandler encryptionResultHandler = new DecryptionResultHandler(TARGET_FIELDS);
            encryptionResultHandler.doHandle(proceed);

        } else if (SqlCommandType.INSERT == mappedStatement.getSqlCommandType() ||
                SqlCommandType.UPDATE == mappedStatement.getSqlCommandType()) {
            // 加密
            Object parameterObject = boundSql.getParameterObject();
            EncryptionParamHandler encryptionParamHandler = new EncryptionParamHandler(TARGET_FIELDS);
            encryptionParamHandler.doHandle(parameterObject);
            // 后执行
            return invocation.proceed();
        }
        return invocation.proceed();
    }


    /**
     * 是否包含某个表
     *
     * @param tables      sql中操作的所有的表
     * @param targetTable 目标表
     * @return 包含true，否则false
     */
    private boolean containsTable(List<String> tables, String targetTable) {
        // 判断查询的表中是否包含目标表
        Optional<String> first = tables.stream().filter(targetTable::equalsIgnoreCase).findFirst();
        return first.isPresent();
    }

    /**
     * 是否包含某个表中的某些字段
     *
     * @param sqlStructure sql解析后的对象
     * @param tableName    某个指定的表
     * @param targetColumn 目标字段
     * @return 包含了目标字段返回true，否则返回false
     */
    private boolean containsColumn(NormalSqlStructure sqlStructure, String tableName, String targetColumn) {
        boolean contains = false;
        // 判断查询的列中是否包含目标列
        List<ColMapping> colMappings = sqlStructure.getColMappings();
        for (ColMapping colMapping : colMappings) {
            Object table = colMapping.getTable();
            if (table instanceof String) {
                if (tableName.equalsIgnoreCase((String) table)) {
                    String column = colMapping.getName();
                    if (column.contains("*")) {
                        contains = true;
                        break;
                    } else {
                        contains = colMapping.getName().equalsIgnoreCase(targetColumn);
                    }
                }
            } else if (table instanceof List) {
                // 这种情况为select * from a, b, c| select * from a left join b inner join c
                // 这种类型的SQL,只要有这个表，就为true
                List<String> tables = (List<String>) table;
                Optional<String> first = tables.stream().filter(tableName::equalsIgnoreCase).findFirst();
                contains = first.isPresent();
            }
        }
        return contains;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
