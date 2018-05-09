package com.udp.frame.demo.service.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;


/**
 * 项目名称:testProject
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/7
 */
public class ServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ch, DatagramPacket datagramPacket) throws Exception {
        System.out.println(datagramPacket.content().toString(CharsetUtil.UTF_8));
        ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("返回2222222",CharsetUtil.UTF_8), datagramPacket.sender()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常断开"+cause);
        ctx.close();
    }
}
