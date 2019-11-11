## 与01-provider的区别
> 使用自建 Spring 容器方式是比较浪费资源的。容器的作用仅仅就是创建一个单例的提供者对象，
其本身并不需要Tomcat或JBoss等Web容器的功能。如果硬要用Web容器去加载服务提供方，就增加了代码的复杂性，
也浪费了资源。 Dubbo提供了一个Main.main()[package com.alibaba.dubbo.container.Main]方法可以直接创建并启动Provider，
其底层仅仅是加载了一个简单的用于暴露服务的Spring容器。该方式要求Spring配置文件必须要放到类路径下的<u>META-INF/spring</u>目录中，
Spring配置文件名称无所谓。