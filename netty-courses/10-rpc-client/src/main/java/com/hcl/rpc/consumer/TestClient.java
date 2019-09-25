package com.hcl.rpc.consumer;

import com.hcl.rpc.api.service.TestService;
import com.hcl.rpc.client.RpcProxy;

/**
 * @author: Hucl
 * @date: 2019/9/25 09:30
 * @description:
 */
public class TestClient {

    public static void main(String[] args) {
        TestService testService = RpcProxy.create(TestService.class);
        String result = testService.saySomething("gay");
        System.out.println(result);
        System.out.println(testService.hashCode());
    }
}
