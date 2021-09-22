package com.example.multidatasource.jdbc;

import com.example.multidatasource.common.Student;
import com.example.multidatasource.common.StudentService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * jdbc template student service
 *
 * @author hucl
 * @date 2021/9/22 3:56 下午
 */
@Service(value = "jdbcTemStudentService")
public class JDBCTemStudentServiceImpl implements StudentService {

    private final JdbcTemplate jdbcTemplate;

    public JDBCTemStudentServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create(String name, Integer age) {
        return jdbcTemplate.update("insert into student(name, age) value(?, ?)", name, age);
    }

    @Override
    public List<Student> getByName(String name) {
        List<Student> studentList = jdbcTemplate.query("select * from student where name = ?", (resultSet, i) -> {
            Student student = new Student();
            student.setName(resultSet.getString("name"));
            student.setId(resultSet.getLong("id"));
            student.setAge(resultSet.getInt("age"));
            return student;
        }, name);
        return studentList;
    }

    @Override
    public int deleteByName(String name) {
        return jdbcTemplate.update("delete from student where name = ?", name);
    }

    @Override
    public Integer getAllStudentCount() {
        return jdbcTemplate.queryForObject("select count(1) from student", Integer.class);
    }

    @Override
    public int deleteAllStudent() {
        return jdbcTemplate.update("delete from student");
    }
}
