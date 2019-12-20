package com.supergo.demo;

import com.supergo.TransactionApplication;
import com.supergo.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by on 2019/6/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TransactionApplication.class)
public class TransactionDemo {

    //注入
    @Autowired
    private UserService userService;

    @Test
    public void testMapper(){
        userService.insert();
    }

}
