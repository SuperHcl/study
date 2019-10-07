package com.hcl.heartbeat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: Hucl
 * @date: 2019/9/24 09:22
 * @description:
 */
public class HeartBeatClientHandler2 extends ChannelInboundHandlerAdapter {

    private ScheduledFuture<?> schedule;

    private GenericFutureListener listener;

    private Bootstrap bootstrap;

    public HeartBeatClientHandler2(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        randomSendHeartBeat(ctx.channel());
    }

    private void randomSendHeartBeat(Channel channel) {
        // 生成一个[1,6)的随机数作为心跳发送间隔
        int heartBeatInternal = new Random().nextInt(6) + 1;
        System.out.println(heartBeatInternal + "秒后将发送下一次心跳");

        schedule = channel.eventLoop().schedule(() -> {
            if (channel.isActive()) {
                System.out.println("向heart beat server发送心跳");
                channel.writeAndFlush("~PING~" + heartBeatInternal);
            } else {
                System.out.println("与heart beat server的连接已经断开");
                channel.closeFuture();
            }
        }, heartBeatInternal, TimeUnit.SECONDS);

        listener = future -> {
            randomSendHeartBeat(channel);
        };

        // 为异步定时任务添加监听器
        schedule.addListener(listener);
    }

    /**
     * 只要channel被钝化（关闭），就会触发该方法的执行
     * @param ctx 上下文
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        schedule.removeListener(listener);
        ctx.channel().eventLoop().schedule(() -> {
            System.out.println("Reconnecting\n");
            try {
                bootstrap.connect("localhost", 1111).sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
        ctx.close();
    }
}
