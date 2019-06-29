package com.hcl.mybatis.jdbc;

import java.sql.*;

/**
 * @author: Hucl
 * @date: 2019/6/29 15:06
 * @description: 原生的jdbc写法
 */
public class NativeJdbc {


    private static final String URL = "jdbc:mysql://localhost:3306/my_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Hcl_0529";


    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            // 获取数据库连接
            connection = getConnection();
            // 定义sql语句
            String sql = "select * from user where id = ?";
            // 获取预处理对象
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            // 执行查询，获取结果集
            rs = preparedStatement.executeQuery();
            // 遍历结果集
            while (rs.next()) {
                System.out.println(rs.getString("id")+"-----"+rs.getString("name"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static Connection getConnection() throws Exception{
        // 加载数据库驱动
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);

    }
}
