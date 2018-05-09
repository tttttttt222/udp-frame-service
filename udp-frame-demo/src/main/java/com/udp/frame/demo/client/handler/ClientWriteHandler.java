package com.udp.frame.demo.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.*;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/9
 */
public class ClientWriteHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private final Timer timer;

    private long timeout;

    public ClientWriteHandler(Timer timer, long timeout) {
        this.timer = timer;
        this.timeout = timeout;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        timer.newTimeout(new FrameSendTask(ctx), timeout, TimeUnit.MICROSECONDS);
    }


    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket datagramPacket) throws Exception {
        ctx.fireChannelRead(datagramPacket);
    }


}
