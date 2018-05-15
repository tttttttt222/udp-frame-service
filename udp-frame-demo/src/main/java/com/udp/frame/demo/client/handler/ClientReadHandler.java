package com.udp.frame.demo.client.handler;

import com.udp.frame.demo.client.ReceiveInfoInterface;
import com.udp.frame.demo.dto.MsgPackage;
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
public class ClientReadHandler extends SimpleChannelInboundHandler<MsgPackage> {

    ReceiveInfoInterface receiveInfoInterface;

    public ClientReadHandler(ReceiveInfoInterface receiveInfoInterface) {
        this.receiveInfoInterface = receiveInfoInterface;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MsgPackage msgPackage) throws Exception {
        System.out.println("接收到数据:"+msgPackage);
        receiveInfoInterface.readInfo(msgPackage.getInfo());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常断开"+cause);
        ctx.close();
    }


}
