package com.hcl.dubbo.server;

import com.hcl.dubbo.registry.RegistryCenter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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
 * @date: 2019/9/27 09:42
 * @description:
 */
public class RpcServer {

    private List<String> classCache = new ArrayList<>();

    private Map<String, Object> registryMap = new HashMap<>();

    private String serviceAddress;

    public void publish(RegistryCenter registryCenter, String serviceAddress, String providerPackage) throws Exception {
        getProviderClass(providerPackage);
        doRegister(registryCenter, serviceAddress);
        this.serviceAddress = serviceAddress;
    }

    private void getProviderClass(String providerPackage) {
        URL resource = this.getClass().getClassLoader().getResource(providerPackage.replaceAll("\\.", "/"));
        if (resource == null) {
            return;
        }

        File dir = new File(resource.getFile());
        File[] listFiles = dir.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    getProviderClass(providerPackage + "." + file.getName());
                } else {
                    String fileName = file.getName().replace(".class", "");
                    classCache.add(providerPackage + "." + fileName);
                }
            }
        }

    }

    private void doRegister(RegistryCenter registryCenter, String serviceAddress) throws Exception {
        if (classCache.size() == 0) return;

        for (String className : classCache) {
            Class<?> aClass = Class.forName(className);
            String serviceName = aClass.getInterfaces()[0].getName();
            registryMap.put(serviceName, aClass.newInstance());
            // 向zk中注册服务提供者，为了对外暴露提供者主机
            registryCenter.register(serviceName, serviceAddress);
        }
    }


    public void start() {
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
                            pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new RpcServerHandler(registryMap));
                        }
                    });

            String ip = serviceAddress.split(":")[0];
            String port = serviceAddress.split(":")[1];

            ChannelFuture future = bootstrap.bind(ip, Integer.valueOf(port)).sync();
            System.out.println("服务已启动,ip = " + ip + " port = " + port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

}
