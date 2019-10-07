package com.hcl.heartbeat.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: Hucl
 * @date: 2019/9/23 17:36
 * @description: 自定义客户端处理器
 * 这种方式当服务器断开连接后，该客户端还是会向服务端发送心跳，并不会停止发送，不友好。
 */
public class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {

    private ScheduledFuture<?> schedule;

    private GenericFutureListener listener;

    // 当channel被激活后会触发该方法的执行
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 随机发送心跳
        randomSendHeartBeat(ctx.channel());
    }

    private void randomSendHeartBeat(Channel channel) {
        // 生成一个[1,6)的随机数作为心跳发送间隔
        int heartBeatInternal = new Random().nextInt(5) + 1;
        System.out.println(heartBeatInternal + "秒后将发送下一次心跳");

        schedule = channel.eventLoop().schedule(() -> {
            if (channel.isActive()) {
                System.out.println("向heart beat server发送心跳");
                channel.writeAndFlush("~PING~");
            } else {
                System.out.println("与heart beat server的连接已经断开");
                // 若channel被关闭，则会触发该future的监听器回调方法的执行
                ChannelFuture channelFuture = channel.closeFuture();
                channelFuture.addListener(future -> {
                   schedule.removeListener(listener);
                });
            }
        }, heartBeatInternal, TimeUnit.SECONDS);

        listener = (future) -> {
            randomSendHeartBeat(channel);
        };

        schedule.addListener(listener);
        // 为异步定时任务添加监听器
//        schedule.addListener((future) -> {
//            /**
//             *  若异步定时任务
//             *  {@link Future#isSuccess} Return true if and only if
//             *  the I/O operation was completed
//             *  表示仅当io流程完成就返回true
//             *  执行成功，则重新再随机发送心跳
//             */
//            if (future.isSuccess()) {
//
//            }
//        });

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        schedule.removeListener(listener);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
