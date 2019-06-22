package com.hcl.order;

import com.hcl.rpc.api.bean.Product;
import com.hcl.rpc.api.service.IProductService;
import com.hcl.rpc.framework.RpcProxy;

/**
 * @author: Hucl
 * @date: 2019/6/22 14:54
 * @description:
 */
public class Main {

    public static void main(String[] args) {
        RpcProxy proxy = new RpcProxy();

        // 获取服务端连接
        // 把要调用的接口传过去
        IProductService productService = (IProductService) proxy.remoteCall(IProductService.class, "localhost", 9000);
        Product product = productService.getById(1001);
        System.out.println(product);
    }
}
