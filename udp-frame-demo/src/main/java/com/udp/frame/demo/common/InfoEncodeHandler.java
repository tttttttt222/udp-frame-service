package com.udp.frame.demo.common;

import com.alibaba.fastjson.JSON;
import com.udp.frame.demo.client.NettyUdpClient;
import com.udp.frame.demo.dto.MsgPackage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.DatagramPacket;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/10
 */
public class InfoEncodeHandler extends ChannelOutboundHandlerAdapter {

    private static Logger logger = Logger.getLogger(InfoEncodeHandler.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object obj, ChannelPromise promise) throws Exception {
        MsgPackage msgPackage=(MsgPackage)obj;
        ByteBuf buf = Unpooled.buffer();
        String jsonString = JSON.toJSONString(msgPackage);
        logger.info("InfoEncodeHandler-发送数据:"+jsonString);
        byte[] bytes = jsonString.getBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        DatagramPacket packet = new DatagramPacket(buf,msgPackage.getAddress());
        super.write(ctx, packet, promise);
    }
}
