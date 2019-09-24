package com.hcl.rpc.server;

/**
 * @author: Hucl
 * @date: 2019/9/24 16:30
 * @description:
 */
public class RpcServerStarter {

    public static void main(String[] args) throws Exception {
        RpcServer rpcServer = new RpcServer();
        rpcServer.publish("com.hcl.rpc.api.service");
        rpcServer.start();
    }
}
