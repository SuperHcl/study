package com.hcl.dubbo.discovery;

import com.hcl.dubbo.constant.ZkConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/9/27 10:31
 * @description:
 */
public class ServiceDiscoveryImpl implements ServiceDiscovery {

    private CuratorFramework curator;
    private List<String> services;

    public ServiceDiscoveryImpl() {
        this.curator = CuratorFrameworkFactory.builder()
                .connectString(ZkConstant.ZK_CLUSTER)
                .connectionTimeoutMs(10000)
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        curator.start();
    }

    @Override
    public String discovery(String serviceName) throws Exception {
        String servicePath = ZkConstant.ZK_DUBBO_ROOT_PATH + "/" + serviceName;

        services = curator.getChildren().forPath(servicePath);
        if (services.size() == 0) return null;

        // 添加watcher监听， 监听子节点列表变化
        registerWatcher(servicePath);
        return new RandomLoadBalance().choose(services);
    }

    private void registerWatcher(String servicePath) throws Exception {
        // 将制定路径下的节点数据内容及状态缓存到本地
        PathChildrenCache childrenCache = new PathChildrenCache(curator, servicePath, true);

        // 一旦监听到子节点列表发生变化，马上更新当前的servers集合
        childrenCache.getListenable().addListener((child, event) ->
                services = curator.getChildren().forPath(servicePath));
        // 启动监听
        childrenCache.start();
    }
}
