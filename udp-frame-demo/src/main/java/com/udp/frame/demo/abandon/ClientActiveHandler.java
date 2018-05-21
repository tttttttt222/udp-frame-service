package com.udp.frame.demo.abandon;

import com.udp.frame.demo.dto.MsgPackage;
import com.udp.frame.demo.service.handler.ServerHandler;
import com.udp.frame.demo.utils.FrameIncrease;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.apache.log4j.Logger;


import java.net.InetSocketAddress;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/9
 */
public class ClientActiveHandler<T> extends SimpleChannelInboundHandler<DatagramPacket> {

    private static Logger logger = Logger.getLogger(ClientActiveHandler.class);

    private final Timer timer;

    private long timeout;


    private MsgPackage msgPackage;

    private FrameIncrease frameIncrease;
    //
//    private String sender;
//
//    private List<String> receivers;
//
//    private InetSocketAddress serverAddress;
//
//    private T data;
//    String sender, List<String> receivers, InetSocketAddress serverAddress, T data,

    public ClientActiveHandler(Timer timer, long timeout,FrameIncrease frameIncrease,MsgPackage msgPackage) {
        this.timer = timer;
        this.timeout = timeout;
        this.frameIncrease = frameIncrease;
        this.msgPackage=msgPackage;

//        this.sender = sender;
//        this.receivers = receivers;
//        this.serverAddress = serverAddress;
//        this.data = data;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        timer.schedule(new FrameSendTask<T>(ctx,data,sender,receivers,serverAddress ,frameIncrease)
//                , timeout,timeout);

        msgPackage.setSeq(System.currentTimeMillis());
        msgPackage.setFrame(frameIncrease.getFrameNo());
        frameIncrease.addFrameNo();
        logger.info("ClientActiveHandler-连接-发送数据:" + msgPackage);
        ctx.writeAndFlush(msgPackage);

    }


    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket datagramPacket) throws Exception {
        ctx.fireChannelRead(datagramPacket);
    }


}
