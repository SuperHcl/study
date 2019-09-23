package com.hcl.tomcat.core;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author: Hucl
 * @date: 2019/9/23 16:04
 * @description:
 */
public class CustomTomcatServer {

    public static void main(String[] args) throws InterruptedException {
        // 默认情况下，Group中会有当前主机逻辑内核数量的2倍的EventLoop
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)
                    // 指定用于存放请求的队列的长度，默认为50，其是Socket的标准参数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 指定使用心跳机制来维护长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 获取ChannelPipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            // HttpServerCodec 是HttpRequestDecoder与HttpResponseEncoder的复合体
                            pipeline.addLast(new HttpServerCodec());
                            // 自定义handler
                            pipeline.addLast(new CustomTomcatServerHandler());
                        }
                    });

            ChannelFuture future = bootstrap.bind(8888).sync();
            System.out.println("tomcat以启动，端口号为8888");
            future.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }


    }
}
