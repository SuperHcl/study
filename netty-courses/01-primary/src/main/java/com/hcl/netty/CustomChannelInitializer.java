package com.hcl.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author: Hucl
 * @date: 2019/9/18 09:38
 * @description: 管道初始化器
 *               当前类的实例在pipeline初始化完毕后就会被GC
 */
public class CustomChannelInitializer extends ChannelInitializer<SocketChannel> {
    // 当channel初始创建完毕后就会触发该方法的执行，用于初始化channel
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 从channel中获取pipeline
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 将HttpServerCodec处理器放入到pipeline的最后
        // HttpServerCodec是什么？是HttpRequestDecoder与HttpResponseEncoder的复合体
        // HttpRequestDecoder：http请求解码器，将Channel中的ByteBuf数据解码为HttpRequest对象
        // HttpResponseEncoder：http响应编码器，将HttpResponse对象编码为将要在Channel中发送的ByteBuf数据
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new CustomServerHandler());
    }
}
