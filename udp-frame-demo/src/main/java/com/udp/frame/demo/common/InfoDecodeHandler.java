package com.udp.frame.demo.common;

import com.alibaba.fastjson.JSON;
import com.udp.frame.demo.dto.MessageProtocol;
import com.udp.frame.demo.dto.MsgPackage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/10
 */
public class InfoDecodeHandler extends SimpleChannelInboundHandler<DatagramPacket> {


    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket datagramPacket) throws Exception {
        MessageProtocol messageProtocol = decodeMessageProtocol(datagramPacket);
        if (messageProtocol == null) {
            return;
        }

        InetSocketAddress inetSocketAddress = decodeAddress(datagramPacket);

        MsgPackage msgPackage = decodeMessagePackage(messageProtocol);
        //发送者地址
        msgPackage.setAddress(inetSocketAddress);
        ctx.fireChannelRead(msgPackage);

    }


    private MessageProtocol decodeMessageProtocol(DatagramPacket datagramPacket) throws Exception {
        ByteBuf content = datagramPacket.content();
        int len = content.readInt();
        if (content.readableBytes() < len) {
            throw new Exception("报文长度小于所传长度");
        }
        byte[] bytes = new byte[len];
        content.readBytes(bytes);
        String con = null;

        con = new String(bytes, "UTF-8");

        MessageProtocol protocol = new MessageProtocol();
        protocol.setLength(len);
        protocol.setMsg(con);
        return protocol;
    }


    private InetSocketAddress decodeAddress(DatagramPacket datagramPacket) {
        return datagramPacket.sender();
    }

    private MsgPackage decodeMessagePackage(MessageProtocol protocol) {
        return JSON.parseObject(protocol.getMsg(), MsgPackage.class);
    }


}
