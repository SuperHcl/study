package com.hcl.rpc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: Hucl
 * @date: 2019/9/24 16:55
 * @description:
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<Object> {

    private Object result;

    public Object getResult() {
        return result;
    }

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.result = msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
