package com.hcl.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author: Hucl
 * @date: 2019/9/18 09:38
 * @description: 自定义服务端处理器
 * 需求：用户提交一个请求后，在浏览器上输出hello netty world
 */
public class CustomServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当channel中有来自客户端的数据时会触发该方法的执行
     * @param ctx 上下文
     * @param msg 来自客户端的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            System.out.println("请求方式："+request.method().name());
            System.out.println("请求url："+request.uri());

            if ("/favicon.ico".equals(request.uri())) {
                System.out.println("不处理favicon.ico请求");
                return;
            }

            // 构造response的响应体
            ByteBuf body = Unpooled.copiedBuffer("Hello netty world", CharsetUtil.UTF_8);
            // 生成响应对象
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);
            // 获取到response的头部后进行初始化
            HttpHeaders headers = response.headers();
            headers.set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            headers.set(HttpHeaderNames.CONTENT_LENGTH, body.readableBytes());

            // 将响应对象写入到channel
            ctx.writeAndFlush(response)
                    .addListener(ChannelFutureListener.CLOSE);

        }

    }

    /**
     * 当channel中的数据在处理过程中出现异常是会触发该方法的执行
     * @param ctx 上下文
     * @param cause 发生的异常对象
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
