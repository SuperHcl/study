package com.hcl.dubbo.server;

import com.hcl.dubbo.registry.ZKRegistryCenter;

/**
 * @author: Hucl
 * @date: 2019/9/27 14:08
 * @description:
 */
public class RpcServerStarter {
    public static void main(String[] args) {

        RpcServer rpcServer = new RpcServer();

        try {
            rpcServer.publish(new ZKRegistryCenter(), "localhost:8888", "com.hcl.dubbo.service");
            rpcServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
