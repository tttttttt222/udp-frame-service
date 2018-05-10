package com.udp.frame.demo.client.handler;

import com.udp.frame.demo.dto.MsgPackage;
import com.udp.frame.demo.utils.FrameIncreaseUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.net.InetSocketAddress;
import java.util.ArrayList;
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


    public FrameSendTask(ChannelHandlerContext ctx, T infoRequest, String sender, List<String> receivers, InetSocketAddress serverAddress) {
        this.ctx = ctx;
        this.infoRequest = infoRequest;
        this.sender = sender;
        this.receivers = receivers;
        this.serverAddress = serverAddress;
    }

    public void run(Timeout timeout) {

        MsgPackage<T> msgPackage = new MsgPackage<T>();
        long i = FrameIncreaseUtil.getInstance().addFrameNo();
        msgPackage.setSender(sender);
        msgPackage.setAddress(serverAddress);
        msgPackage.setReceivers(receivers);
        msgPackage.setFrame(i);
        msgPackage.setSeq(System.currentTimeMillis());
        msgPackage.setInfo(infoRequest);

        ctx.writeAndFlush(msgPackage);
    }
}
