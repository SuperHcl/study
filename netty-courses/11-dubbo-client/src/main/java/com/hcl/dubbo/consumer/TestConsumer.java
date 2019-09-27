package com.hcl.dubbo.consumer;

import com.hcl.dubbo.client.RpcProxy;
import com.hcl.dubbo.service.TestService;

/**
 * @author: Hucl
 * @date: 2019/9/27 13:57
 * @description:
 */
public class TestConsumer {

    public static void main(String[] args) {
        TestService service = RpcProxy.create(TestService.class);

        System.out.println(service.saySomething("dubbo"));
        System.out.println(service.hashCode());
    }
}
