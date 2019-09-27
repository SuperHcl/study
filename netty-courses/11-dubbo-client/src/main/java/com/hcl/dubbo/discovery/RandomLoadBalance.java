package com.hcl.dubbo.discovery;

import java.util.List;
import java.util.Random;

/**
 * @author: Hucl
 * @date: 2019/9/27 10:27
 * @description: 随机负载均衡策略
 */
public class RandomLoadBalance implements LoadBalance{

    @Override
    public String choose(List<String> servers) {
        return servers.get(new Random().nextInt(servers.size()));
    }
}
