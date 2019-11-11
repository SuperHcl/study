package com.hcl.service;

/**
 * @author: Hucl
 * @date: 2019/11/11 14:46
 * @description:
 */
public class SupermanServiceImpl implements SupermanService {
    @Override
    public String hello(String name) {
        System.out.println(name + " 我是服务提供者");
        return "Hello Dubbo World " + name;
    }
}
