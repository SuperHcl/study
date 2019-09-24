package com.hcl.rpc.client;

import com.hcl.rpc.api.bean.InvokeMessage;
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
 * @date: 2019/9/24 16:46
 * @description:
 */
public class RpcProxy {

    @SuppressWarnings("unchecked")
    public static <T> T create(final Class<?> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 当调用的是Object中的方法，则直接进行本地调用
                        if (Object.class.equals(method.getDeclaringClass())) {
                            // 本地调用
                            return method.invoke(this, args);
                        }
                        // 远程调用
                        return rpcInvoke(clazz, method, args);
                    }
                });
    }

    private static Object rpcInvoke(Class<?> clazz, Method method, Object[] args) {
        final RpcClientHandler handler = new RpcClientHandler();
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(handler);

                        }
                    });
            ChannelFuture future = bootstrap.connect("localhost", 8888).sync();

            future.channel().writeAndFlush(getInvokeMessage(clazz, method, args)).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

        return handler.getResult();
    }

    private static InvokeMessage getInvokeMessage(Class<?> clazz, Method method, Object[] args) {
        InvokeMessage message = new InvokeMessage();
        message.setClassName(clazz.getName());
        message.setMethodName(method.getName());
        message.setParamTypes(message.getParamTypes());
        message.setParamValues(args);
        return message;
    }
}
