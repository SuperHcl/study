package com.hcl.tomcat.core;

import com.hcl.tomcat.service.TestService;
import com.hcl.tomcat.servlet.CustomHttpRequest;
import com.hcl.tomcat.servlet.CustomHttpResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

/**
 * @author: Hucl
 * @date: 2019/9/23 16:11
 * @description:
 */
public class CustomTomcatServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;

            TestService service = new TestService();
            service.doGet(new CustomHttpRequest(request, ctx),
                    new CustomHttpResponse(request, ctx));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
