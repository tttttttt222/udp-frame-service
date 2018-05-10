package com.udp.frame.demo.service.handler;

import com.udp.frame.demo.common.ChannelMap;
import com.udp.frame.demo.dto.MsgPackage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;


/**
 * 项目名称:testProject
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/7
 */
public class ServerHandler extends SimpleChannelInboundHandler<MsgPackage> {



    @Override
    protected void channelRead0(ChannelHandlerContext ch, MsgPackage msgPackage){
        List<String> receivers = msgPackage.getReceivers();
        for (String receiver : receivers) {
            Map<String, InetSocketAddress> chmap = ChannelMap.getInstance().getChmap();
            InetSocketAddress inetSocketAddress = chmap.get(receiver);
            if (inetSocketAddress != null){
                //目标地址
                msgPackage.setAddress(inetSocketAddress);
                ch.writeAndFlush(msgPackage);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
//        System.out.println("异常断开"+cause);
//        ctx.close();
    }
}
