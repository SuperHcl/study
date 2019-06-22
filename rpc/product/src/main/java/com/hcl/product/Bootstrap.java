package com.hcl.product;

import com.hcl.product.service.impl.ProductServiceImpl;
import com.hcl.rpc.framework.Registry;
import com.hcl.rpc.framework.RpcServer;

/**
 * @author: Hucl
 * @date: 2019/6/22 14:56
 * @description: 服务端
 */
public class Bootstrap {

    public static void main(String[] args) {
        Registry.classMap.put("com.hcl.rpc.api.service.IProductService", ProductServiceImpl.class);
        RpcServer rpcServer = new RpcServer();
        rpcServer.start(9000);
    }
}
