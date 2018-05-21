package com.udp.frame.demo.service.handler;

import com.udp.frame.demo.common.ChannelMap;
import com.udp.frame.demo.common.InfoEncodeHandler;
import com.udp.frame.demo.dto.MsgPackage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.Map;


/**
 * 项目名称:udp-frame-demo
 * 描述:处理新连接数据
 * 创建人:ryw
 * 创建时间:2018/5/10
 */
public class PerosnInHandler extends SimpleChannelInboundHandler<MsgPackage> {

    private static Logger logger = Logger.getLogger(PerosnInHandler.class);

    protected void channelRead0(ChannelHandlerContext ctx, MsgPackage msgPackage){
        logger.info("服务器接收到数据:"+msgPackage);
        //第0帧数据只保存服务信息
        if (msgPackage.getType() == 1){
            Map<String, InetSocketAddress> chmap = ChannelMap.getInstance().getChmap();
            chmap.put(msgPackage.getSender(),msgPackage.getAddress());
            msgPackage.setAddress(chmap.get(msgPackage.getSender()));
            msgPackage.setType(1);
            ctx.writeAndFlush(msgPackage);
        }else {
            ctx.fireChannelRead(msgPackage);
        }
    }


}
