package com.udp.frame.demo.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.net.InetSocketAddress;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/9
 */
public class FrameSendTask implements TimerTask {


    private ChannelHandlerContext ctx;

    public FrameSendTask(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public void run(Timeout timeout) throws Exception {
        ByteBuf buf = Unpooled.copiedBuffer("发送111111111", CharsetUtil.UTF_8);
        DatagramPacket packet = new DatagramPacket(buf, new InetSocketAddress("127.0.0.1", 9999));
        ctx.writeAndFlush(packet);
    }
}
