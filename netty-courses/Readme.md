## 11-dubbo-*
> 在11-dubbo-*的项目中，用的zookeeper的版本是<label style="color:red">3.4.9</label>。之前用的版本是3.4.14，但是这个版本在使用java代码创建临时节点的时候，老创建不上。具体代码如下
---
```
// 创建ip+port的临时节点
String nodeName = curator.create()
          // CreateMode.EPHEMERAL 临时节点
          /**
            * The znode will be deleted upon the client's disconnect.
            */
          .withMode(CreateMode.EPHEMERAL)
          .forPath(addressPath, addressPath.getBytes());
```
