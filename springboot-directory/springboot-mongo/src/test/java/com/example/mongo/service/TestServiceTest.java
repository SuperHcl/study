package com.example.mongo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author: Hucl
 * @date: 2019/10/14 16:24
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestServiceTest {
    @Autowired
    private TestService testService;

    @Test
    public void query() {
        testService.query1();
    }
}