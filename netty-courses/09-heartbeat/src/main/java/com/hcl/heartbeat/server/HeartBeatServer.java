package com.hcl.heartbeat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author: Hucl
 * @date: 2019/9/23 17:17
 * @description:
 */
public class HeartBeatServer {

    public static void main(String[] args) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast(new HttpServerCodec());
                            // 接受客户端发送过来的请求信息，应该是解码StringDecoder()
                            pipeline.addLast(new StringDecoder());
                            /**
                             * 单位秒
                             * @param readerIdleTimeSeconds 读取超时时间(No data was received for a while)
                             * @param writerIdleTimeSeconds 写超时时间(No data was sent for a while)
                             * @param allIdleTimeSeconds 即没读也没写(No data was either received or sent for a while)
                             * new IdleStateHandler(int readerIdleTimeSeconds,
                             *             int writerIdleTimeSeconds,
                             *             int allIdleTimeSeconds)
                             */
                            pipeline.addLast(new IdleStateHandler(5, 0,0));
                            pipeline.addLast(new HeartBeatServerHandler());
                        }
                    });

            ChannelFuture future = bootstrap.bind(1111).sync();
            System.out.println("服务已启动，端口号为1111");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
