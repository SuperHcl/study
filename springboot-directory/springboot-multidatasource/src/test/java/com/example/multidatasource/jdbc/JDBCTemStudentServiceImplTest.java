package com.example.multidatasource.jdbc;

import com.example.multidatasource.common.Student;
import com.example.multidatasource.common.StudentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


/**
 * jdbc template tests
 *
 * @author hucl
 * @date 2021/9/22 4:16 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JDBCTemStudentServiceImplTest {

    @Resource
    private StudentService jdbcTemStudentService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void create() {
        jdbcTemStudentService.create("Tom", 22);
        jdbcTemStudentService.create("Jack", 21);
        jdbcTemStudentService.create("Marry", 18);
        jdbcTemStudentService.create("Jane", 23);
        jdbcTemStudentService.create("Mark", 22);

    }

    @Test
    public void getByName() {
        List<Student> tom = jdbcTemStudentService.getByName("Tom");
        Assert.assertEquals(1, tom.size());
    }

    @Test
    public void deleteByName() {
        int jane = jdbcTemStudentService.deleteByName("Jane");
        assert jane > 0;

    }

    @Test
    public void getAllStudentCount() {
        Integer allStudentCount = jdbcTemStudentService.getAllStudentCount();
        assert allStudentCount == 4;
    }

    @Test
    public void deleteAllStudent() {
        int i = jdbcTemStudentService.deleteAllStudent();
        System.out.println(i);
    }
}