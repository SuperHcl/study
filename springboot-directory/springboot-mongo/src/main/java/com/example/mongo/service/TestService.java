package com.example.mongo.service;

import com.example.mongo.entity.Study;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/10/14 16:06
 * @description:
 */
@Service
public class TestService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void query1() {
        List<Study> all = mongoTemplate.findAll(Study.class, "mycollection");
        for (Study study : all) {
            System.out.println(study.toString());
        }
    }

    public void query2() {
    }
}
