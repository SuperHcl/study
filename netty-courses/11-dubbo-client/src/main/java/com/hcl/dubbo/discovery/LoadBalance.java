package com.hcl.dubbo.discovery;

import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/9/27 10:24
 * @description:
 */
public interface LoadBalance {

    /**
     * 从servers中通过负载均衡策略选择一个主机地址
     * @param servers server 服务端提供的
     * @return
     */
    String choose(List<String> servers);
}
