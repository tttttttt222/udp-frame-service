package com.udp.frame.demo.service.handler;

import com.udp.frame.demo.common.ChannelMap;
import com.udp.frame.demo.dto.MsgPackage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 项目名称:udp-frame-demo
 * 描述:处理新连接数据
 * 创建人:ryw
 * 创建时间:2018/5/10
 */
public class PerosnInHandler extends SimpleChannelInboundHandler<MsgPackage> {



    protected void channelRead0(ChannelHandlerContext ctx, MsgPackage msgPackage){
        System.out.println("服务器接收到数据:"+msgPackage);
        if (msgPackage.getType() == 1){
            ChannelMap.getInstance().getChmap().put(msgPackage.getSender(),msgPackage.getAddress());
        }else {
            ctx.fireChannelRead(msgPackage);
        }
    }


}
