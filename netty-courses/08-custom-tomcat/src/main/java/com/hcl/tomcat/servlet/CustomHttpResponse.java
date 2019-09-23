package com.hcl.tomcat.servlet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author: Hucl
 * @date: 2019/9/23 15:04
 * @description: 自定义HttpResponse
 */
public class CustomHttpResponse {
    private HttpRequest request;
    private ChannelHandlerContext context;

    public CustomHttpResponse(HttpRequest request, ChannelHandlerContext context) {
        this.request = request;
        this.context = context;
    }

    public void write(String content) {
        // 构造response的响应体
        ByteBuf body = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
        // 创建响应对象
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);

        HttpHeaders headers = response.headers();
        // 设置响应内容类型
        headers.set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN);
        // 设置响应体长度
        headers.set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        // 设置缓存过期时间
        headers.set(HttpHeaderNames.EXPECT, 0);
        // 若HTTP请求连接是长连接，则设置响应连接也为长连接
        if (HttpUtil.isKeepAlive(request)) {
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        // 将相应对象写入到channel
        context.writeAndFlush(response);
    }
}
