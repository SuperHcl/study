package com.hcl.mybatis.sqlsession;

import com.hcl.mybatis.config.*;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/7/2 17:18
 * @description:
 */
public class SimpleExecutor implements Executor {
    @Override
    public <T> List<T> doQuery(Configuration configuration, MappedStatement mappedStatement, Object param) {
        /*
         * a).获取连接 //读取配置文件，获取数据源对象，根据数据源获取连接 通过Configuration对象获取DataSource对象
         * 通过DataSource对象，获取Connection
         */
        Connection connection = null;

        List<Object> results = new ArrayList<>();

        DataSource dataSource = configuration.getDataSource();
        try {
            connection = dataSource.getConnection();
            // 获取sql语句
            SqlSource sqlSource = mappedStatement.getSqlSource();
            BoundSql boundSql = sqlSource.getBoundSql();
            String sql = boundSql.getSql();

            String statementType = mappedStatement.getStatementType();
            if ("prepared".equals(statementType)) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                // 获取设置的参数
                List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
                // 获取参数类型
                Class<?> parameterTypeClass = mappedStatement.getParameterTypeClass();
                // 八种基本类型的处理
                // 如果入参的类型是八种基本类型的，就说明入参是一个参数
                if (parameterTypeClass == Integer.class ) {
                    // todo 有待优化
                    preparedStatement.setInt(1, (Integer) param);
                } else if (parameterTypeClass == String.class) {
                    // preparedStatement.setString(1, (String)param);
                    // 针对字符串 设置setNString() 可以不考虑编码问题。中文能从数据库中查到。
                    preparedStatement.setNString(1, (String)param);
                } else {
                    // 如果是对象类型的。 Map List等集合类型的暂不处理
                    for (int i = 0; i < parameterMappings.size(); i++) {
                        ParameterMapping parameterMapping = parameterMappings.get(i);
                        // 得到属性名称
                        String name = parameterMapping.getName();
                        // 通过反射获取入参对象中执行名称的属性值
                        Field field = parameterTypeClass.getDeclaredField(name);
                        // 设置暴力赋值，不管属性是不是私有
                        field.setAccessible(true);
                        Object value = field.get(param);
                        preparedStatement.setObject(i+1, value);
                    }
                }
                ResultSet rs = preparedStatement.executeQuery();
                Class<?> resultTypeClass = mappedStatement.getResultTypeClass();
                while (rs.next()) {
                    Object returnObj = resultTypeClass.newInstance();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int count = metaData.getColumnCount();

                    for (int i = 1; i <= count; i++) {
                        String columnName = metaData.getColumnName(i);
                        Field field = resultTypeClass.getDeclaredField(columnName);
                        field.setAccessible(true);
                        field.set(returnObj, rs.getObject(columnName));
                    }

                    results.add(returnObj);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return (List<T>) results;
    }
}
