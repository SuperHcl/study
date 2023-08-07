package com.example.springbootmybatis.interceptor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author Hu.ChangLiang
 * @date 2023/5/31 16:22
 */
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
public class EncryptionInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();

        // 获取查询结果集
        Object resultSet = invocation.getArgs()[0];

        if (resultSet instanceof ResultSet) {
            ResultSet rs = (ResultSet) resultSet;
            while (rs.next()) {
                // 对每一行的 name 字段进行加密处理
                String name = rs.getString("name");
                String encryptedName = encryptName(name);

                // 更新结果集中的加密后的值
                rs.updateString("name", encryptedName);
                rs.updateRow();
            }
        }

        return result;
    }

    private String encryptName(String name) {
        // 在这里进行加密逻辑
        // ...
        return name + "--asd";
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 可以在这里设置拦截器的配置属性
    }
}

