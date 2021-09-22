package com.example.multidatasource.common;

import java.util.List;

/**
 * @author hucl
 * @date 2021/9/22 3:48 下午
 */
public interface StudentService {

    /**
     * 创建
     *
     * @param name 姓名
     * @param age  年龄
     * @return 创建结果
     */
    int create(String name, Integer age);

    /**
     * 根据姓名查询
     *
     * @param name 学生姓名
     * @return 学生列表
     */
    List<Student> getByName(String name);

    /**
     * 根据姓名删除
     *
     * @param name 姓名
     * @return 删除结果
     */
    int deleteByName(String name);

    /**
     * 获取所有学生的数量
     *
     * @return 所有学生数量
     */
    Integer getAllStudentCount();

    /**
     * 删除所有学生
     *
     * @return 删除的数量
     */
    int deleteAllStudent();


}
