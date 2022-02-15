package com.example.springbootmybatis.repository.mapper;

import com.example.springbootmybatis.repository.dao.StudentDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Hu.ChangLiang
 * @date 2021/9/30 2:12 下午
 */

public interface StudentMapper {

    StudentDAO findById(@Param("id") Long id);

    List<StudentDAO> findByParam(@Param("params") Map<String, Object> params);

    List<StudentDAO> findByStudent(/*@Param("student") */StudentDAO params);

    StudentDAO findOneByStudent(StudentDAO studentDAO);
}
