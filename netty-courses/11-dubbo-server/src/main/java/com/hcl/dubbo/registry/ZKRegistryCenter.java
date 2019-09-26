package com.hcl.dubbo.registry;

import com.hcl.dubbo.constant.ZkConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author: Hucl
 * @date: 2019/9/26 17:07
 * @description:
 */
public class ZKRegistryCenter implements RegistryCenter {
    // 声明zk客户端
    private CuratorFramework curator;

    {
        curator = CuratorFrameworkFactory.builder()
                .connectString(ZkConstant.ZK_CLUSTER)
                // 连接超时时间，默认15秒
                .connectionTimeoutMs(10000)
                // 会话超时时间， 默认60s
                .sessionTimeoutMs(4000)
                // 设置重试机制：每1秒重试一次，最多重试10次
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        curator.start();
    }

    @Override
    public void register(String serviceName, String serviceAddress) throws Exception {
        String servicePath = ZkConstant.ZK_DUBBO_ROOT_PATH + "/" +serviceName;

        // 若节点不存在，则创建。
        // 如果有两个节点同时到达zookeeper，则zk只会创建一个，但是另一个是创建失败，不是不创建了。
        // 所以在此加个判断，若不存在 则创建。效率会提升
        if (curator.checkExists().forPath(servicePath) ==  null) {
            // 创建服务名称的持久节点
            curator.create()
                    // 若父节点不存在，则创建父节点
                    .creatingParentsIfNeeded()
                    // 创建持久节点
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(servicePath, "0".getBytes());
        }

        // 要创建类似于/dubbocustom/com.hcl.dubbo.service.TestService/192.168.121.24:8081节点
        String addressPath = servicePath + "/" + serviceAddress;

        // 创建ip+port的临时节点
        String nodeName = curator.create()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(addressPath, "0".getBytes());

        System.out.println("提供者主机节点创建成功：" + nodeName);
    }
}
