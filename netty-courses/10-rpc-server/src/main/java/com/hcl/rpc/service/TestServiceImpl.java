package com.hcl.rpc.service;

import com.hcl.rpc.api.service.TestService;

/**
 * @author: Hucl
 * @date: 2019/9/24 16:27
 * @description:
 */
public class TestServiceImpl implements TestService {
    @Override
    public String saySomething(String something) {
        return "What are you fucking say? " + something;
    }
}
