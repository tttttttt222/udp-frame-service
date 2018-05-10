package com.udp.frame.demo.client.handler;

import com.udp.frame.demo.dto.MsgPackage;
import com.udp.frame.demo.utils.FrameIncrease;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/9
 */
public class FrameSendTask<T> implements TimerTask {


    private ChannelHandlerContext ctx;

    private T infoRequest;

    private String sender;

    private List<String> receivers;

    private InetSocketAddress serverAddress;

    private FrameIncrease frameIncrease;

    public FrameSendTask(ChannelHandlerContext ctx, T infoRequest, String sender, List<String> receivers, InetSocketAddress serverAddress ,FrameIncrease frameIncrease) {
        this.ctx = ctx;
        this.infoRequest = infoRequest;
        this.sender = sender;
        this.receivers = receivers;
        this.serverAddress = serverAddress;
        this.frameIncrease = frameIncrease;
    }

    public void run(Timeout timeout) {

        MsgPackage<T> msgPackage = new MsgPackage<T>();
        msgPackage.setSender(sender);
        msgPackage.setAddress(serverAddress);
        msgPackage.setReceivers(receivers);

        if(frameIncrease.getFrameNo() == 0L){
            msgPackage.setType(1);
        }else{
            msgPackage.setType(0);
        }

        long i = frameIncrease.addFrameNo();
        msgPackage.setFrame(i);
        msgPackage.setSeq(System.currentTimeMillis());
        msgPackage.setInfo(infoRequest);

        System.out.println();
        ctx.writeAndFlush(msgPackage);
    }
}
