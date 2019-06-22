#  简单模拟RPC的实现

### 现阶段所用技术

* 网络通信技术：Socket(BIO)
* 消息协议：实现Serializable进行序列化与反序列化,通过ObjectInputStream/ObjectOutputStream读写
* 注册中心：使用CurrentHashMap进行注册 key->类的全路径，value->Class
* 代理方式：JDK动态代理
### 项目结构
* api 提供的服务接口
* order 客户端
* product 服务端. 实现api中的接口，并提供给客户端使用.
* rpc-framework 
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)

