package com.udp.frame.demo.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/9
 */
public class ClientWriteHandler extends SimpleChannelInboundHandler<DatagramPacket> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = Unpooled.copiedBuffer("发送111111111", CharsetUtil.UTF_8);
        DatagramPacket packet = new DatagramPacket(buf, new InetSocketAddress("127.0.0.1", 9999));
        ctx.writeAndFlush(packet);
    }


    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket datagramPacket) throws Exception {
        ctx.fireChannelRead(datagramPacket);
    }


}
