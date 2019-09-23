package com.hcl.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author: Hucl
 * @date: 2019/9/18 09:38
 * @description: 服务启动类
 */
public class CustomServer {


    public static void main(String[] args) throws InterruptedException {

        // 用于处理客户端连接请求，将请求发送给childGroup中的eventLoop
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        // 用于处理客户端请求
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)                // 指定eventLoopGroup
                    .channel(NioServerSocketChannel.class)          // 指定使用NIO进行通信
                    .childHandler(new CustomChannelInitializer());  // 指定childGroup中的eventLoop所绑定的线程所要处理的处理器

            // 指定当前服务器所监听的端口号
            // bind()方法的执行是异步的
            // sync()方法会使bind()操作与后续的代码的执行由异步变为同步
            ChannelFuture future = bootstrap.bind(8888).sync();
            System.out.println("服务器启动成功。监听端口号为：8888");
            future.channel().closeFuture().sync();
        } finally {
            // 优雅关闭（等线程运行结束后在会关闭，并不会强制立马关闭）
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }

    }
}
