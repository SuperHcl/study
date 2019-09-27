package com.hcl.dubbo.client;

import com.hcl.dubbo.bean.InvokeMessage;
import com.hcl.dubbo.discovery.ServiceDiscovery;
import com.hcl.dubbo.discovery.ServiceDiscoveryImpl;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: Hucl
 * @date: 2019/9/27 11:05
 * @description:
 */
public class RpcProxy {

    @SuppressWarnings("all")
    public static <T> T create(Class<?> clazz) {

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 本地方法
                if (Object.class.equals(clazz.getDeclaringClass())) {
                    return method.invoke(this, args);
                } else {
                    // 远程调用
                    return rpcInvoke(clazz, method, args);
                }
            }
        });

    }

    private static Object rpcInvoke(Class<?> clazz, Method method, Object[] args) {
        RpcClientHandler handler = new RpcClientHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(handler);

                        }
                    });
            ServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl();
            String serviceAddr = serviceDiscovery.discovery(clazz.getName());
            String ip = serviceAddr.split(":")[0];
            String port = serviceAddr.split(":")[1];

            ChannelFuture future = bootstrap.connect(ip, Integer.valueOf(port)).sync();
            // 创建并初始化调用信息
            InvokeMessage message = new InvokeMessage();
            message.setClassName(clazz.getName());
            message.setMethodName(method.getName());
            message.setParamTypes(method.getParameterTypes());
            message.setParamValues(args);

            future.channel().writeAndFlush(message).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

        return handler.getResult();
    }
}
