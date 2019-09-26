package com.hcl.dubbo.server;

import com.hcl.dubbo.bean.InvokeMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

/**
 * @author: Hucl
 * @date: 2019/9/24 15:18
 * @description:
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<InvokeMessage> {

    private Map<String, Object> registryMap;

    public RpcServerHandler(Map<String, Object> registryMap) {
        this.registryMap = registryMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InvokeMessage msg) throws Exception {
        Object result = "没有找到指定的服务提供者";
        // 判断注册中心是否有指定的服务
        if (registryMap.containsKey(msg.getClassName())) {
            // 获取服务提供者实例
            Object provider = registryMap.get(msg.getClassName());
            // 利用反射进行方法的执行
            result = provider.getClass().getMethod(msg.getMethodName(), msg.getParamTypes())
                    .invoke(provider, msg.getParamValues());
        }
        // 返回给客户端
        ctx.writeAndFlush(result);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
