package com.hcl.dubbo.service;

/**
 * @author: Hucl
 * @date: 2019/9/26 17:01
 * @description:
 */
public class TestServiceImpl implements TestService{
    @Override
    public String saySomething(String something) {
        return "custom dubbo framework " + something;
    }
}
