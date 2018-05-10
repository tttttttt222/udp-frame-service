package com.udp.frame.demo.client.handler;

import com.udp.frame.demo.utils.FrameIncrease;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;


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
public class ClientWriteHandler<T> extends SimpleChannelInboundHandler<DatagramPacket> {

    private final Timer timer;

    private long timeout;

    private String sender;

    private List<String> receivers;

    private InetSocketAddress serverAddress;

    private T data;

    private FrameIncrease frameIncrease;

    public ClientWriteHandler(Timer timer, long timeout, String sender, List<String> receivers, InetSocketAddress serverAddress , T data , FrameIncrease frameIncrease) {
        this.timer = timer;
        this.timeout = timeout;
        this.sender = sender;
        this.receivers = receivers;
        this.serverAddress = serverAddress;
        this.data=data;
        this.frameIncrease =frameIncrease;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        timer.schedule(new FrameSendTask<T>(ctx,data,sender,receivers,serverAddress ,frameIncrease)
                , timeout,2);
    }


    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket datagramPacket) throws Exception {
        ctx.fireChannelRead(datagramPacket);
    }


}
