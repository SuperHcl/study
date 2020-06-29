# Eureka Registry Center 8001
```
client:
    register-with-eureka: false     # 指定是否向注册中心注册自己
    fetch-registry: false       # 指定此客户端是否能够获取eureka注册信息
    service-url:    # 暴露服务中心地址 [集群模式]
      defaultZone: http://localhost:8000/eureka,http://localhost:8001/eureka
```