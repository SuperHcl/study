package com.hcl.rpc.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Hucl
 * @date: 2019/9/24 14:02
 * @description:
 */
public class RpcServer {
    // 用于存放指定包下包中所有接口实现类的类名
    private List<String> classCache = new ArrayList<>();

    // 注册中心（注册表）
    // key为服务名称，即业务接口名，value为对应接口实现类的实例
    private Map<String, Object> registryMap = new HashMap<>();


    /**
     * 服务发布
     * 将服务名称与对应包中实现类的实例的映射关系写入到注册中心的过程
     *
     * @param providerPackage 提供的包路径
     * @throws Exception
     */
    public void publish(String providerPackage) throws Exception {
        // 将制定包下的所有实现类名称写入到classCache集合
        getProviderClass(providerPackage);
        // 讲服务提供者注册到注册表中
        doRegister();
    }

    // 将指定包下的所有实现类名称写入到classCache集合
    private void getProviderClass(String providerPackage) {
        URL resource = this.getClass().getClassLoader().getResource(providerPackage.replaceAll("\\.", "/"));

        if (resource == null) {
            return;
        }
        // 将URL对象转化为File
        File dir = new File(resource.getFile());
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        // 遍历dir目录中的所有文件，查找.class文件
        for (File file : files) {
            // 若当前文件是目录，则递归
            if (file.isDirectory()) {
                getProviderClass(providerPackage + "." + file.getName());
            } else {
                // 将文件名中的.class扩展名去掉，即获取到简单的类名
                String fileName = file.getName().replace(".class", "").trim();
                // 将实现类的全限定性类名写入到classCache中
                classCache.add(providerPackage + "." + fileName);
            }
        }

        System.out.println(classCache);

    }

    // 将服务提供者注册到注册表
    private void doRegister() {
        if (classCache.size() == 0) return;

        // 遍历classCache, 获取达到实现类所实现的接口名称，及创建该实现类对应实例
        classCache.forEach(s -> {
            try {
                Class<?> clazz = Class.forName(s);
                registryMap.put(clazz.getInterfaces()[0].getName(), clazz.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void start() throws InterruptedException {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // ObjectDecoder() 对象解码器
                            // ClassResolvers.cacheDisabled(null)的作用指定默认类加载器，将客户端发送的调用信息类加载到内存
                            pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new RpcServerHandler(registryMap));
                        }
                    });
            ChannelFuture future = bootstrap.bind(8888).sync();
            System.out.println("服务已启动，端口号为8888");
            future.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        RpcServer server = new RpcServer();
        server.getProviderClass("com.hcl.rpc.api.service");

    }

}
