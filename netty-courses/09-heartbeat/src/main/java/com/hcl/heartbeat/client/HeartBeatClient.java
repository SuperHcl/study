package com.hcl.heartbeat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author: Hucl
 * @date: 2019/9/23 17:17
 * @description:
 */
public class HeartBeatClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        /*
                         * 客户端向服务端发送信息，应该是StringEncoder()
                         */
                        // StringDecoder() 读（解码）
                        // pipeline.addLast(new StringDecoder());
                        // StringEncoder() 写（转码）
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new HeartBeatClientHandler());
                    }
                });
        bootstrap.connect("localhost", 1111).sync();
    }
}
