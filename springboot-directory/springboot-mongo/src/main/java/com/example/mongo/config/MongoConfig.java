//package com.example.mongo.config;
//
//import com.mongodb.MongoClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
//
///**
// * @author: Hucl
// * @date: 2019/10/14 15:51
// * @description:
// */
//
//@Configuration
//public class MongoConfig {
//
//
//    @Bean
//    @Primary
//    public MongoTemplate template() {
//        return new MongoTemplate(factory());
//    }
//
//
//    @Bean("mongoDbFactory")
//    public MongoDbFactory factory() {
//        return new SimpleMongoDbFactory(client(), "hcl");
//    }
//
//    @Bean("mongoClient")
//    public MongoClient client() {
//        return new MongoClient("47.94.98.106", 27017);
//    }
//}
