package com.udp.frame.demo.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;


/**
 * 项目名称:testProject
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/8
 */
public class ClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = Unpooled.copiedBuffer("发送111111111", CharsetUtil.UTF_8);
        DatagramPacket packet = new DatagramPacket(buf,new InetSocketAddress("127.0.0.1",9999));
        ctx.writeAndFlush(packet);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        System.out.println(datagramPacket.content().toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常断开"+cause);
        ctx.close();
    }
}
