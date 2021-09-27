package com.example.multidatasource.mybatis;

import com.example.multidatasource.common.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Hu.ChangLiang
 * @date 2021/9/24 4:35 下午
 */
@Mapper
public interface StudentMapper {

    @Select("select * from student where name = #{inner.student.name}")
    Student findByName(@Param("inner") InnerStudent student);

    @Select("select * from student where id = #{id}")
    Student findById(Long id);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age")
    })
    @Select("select * from student")
    List<Student> findAll();


    @Insert("insert into student(name, age) values(#{name}, #{age})")
    int insert(@Param("name") String name, @Param("age") Integer age);

    @Update("update student set name=#{name}, age=#{age} where id = #{id}")
    int updateById(Student student);


    @Delete("delete from student where id=#{id}")
    int deleteById(@Param("id") Long id);
}
