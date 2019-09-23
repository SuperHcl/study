package com.hcl.heartbeat.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: Hucl
 * @date: 2019/9/23 17:36
 * @description:
 */
public class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {

    // 当channel被激活后会触发该方法的执行
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 随机发送心跳
        randomSendHeartBeat(ctx.channel());
    }

    private void randomSendHeartBeat(Channel channel) {
        // 生成一个[1,8)的随机数作为心跳发送间隔
        int heartBeatInternal = new Random().nextInt(5) + 1;
        System.out.println(heartBeatInternal + "秒后将发送下一次心跳");

        ScheduledFuture<?> schedule = channel.eventLoop().schedule(() -> {
            if (channel.isActive()) {
                System.out.println("向heart beat server发送心跳");
                channel.writeAndFlush("~PING~");
            } else {
                System.out.println("与heart beat server的连接已经断开");
                channel.closeFuture();
            }
        }, heartBeatInternal, TimeUnit.SECONDS);

        // 为异步定时任务添加监听器
        schedule.addListener((future) -> {
            // 若异步定时任务执行成功，则重新再随机发送心跳
            if (future.isSuccess()) {
                randomSendHeartBeat(channel);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
