package com.hcl.dubbo.discovery;

/**
 * @author: Hucl
 * @date: 2019/9/27 10:30
 * @description:
 */
public interface ServiceDiscovery {

    /**
     * 根据服务名称查找提供者主机地址
     * @param serviceName 服务名
     * @return
     * @throws Exception
     */
    String discovery(String serviceName) throws Exception;
}
