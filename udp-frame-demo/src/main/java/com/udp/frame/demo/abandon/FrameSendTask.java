package com.udp.frame.demo.abandon;

import com.udp.frame.demo.dto.MsgPackage;
import com.udp.frame.demo.utils.FrameIncrease;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;


import java.net.InetSocketAddress;
import java.util.List;
import java.util.TimerTask;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/9
 */
public class FrameSendTask<T> extends TimerTask {

    private static Logger logger = Logger.getLogger(FrameSendTask.class);

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

    public void run() {

        MsgPackage<T> msgPackage = new MsgPackage<T>();
        msgPackage.setSender(sender);
        msgPackage.setAddress(serverAddress);
        msgPackage.setReceivers(receivers);

        if(frameIncrease.getFrameNo() == 0L){
            msgPackage.setType(1);
        }else{
            msgPackage.setType(0);
        }

        msgPackage.setFrame(frameIncrease.getFrameNo());
        frameIncrease.addFrameNo();
        msgPackage.setSeq(System.currentTimeMillis());
        msgPackage.setInfo(infoRequest);

        logger.info("FrameSendTask-发送数据:"+msgPackage);
        ctx.writeAndFlush(msgPackage);
    }
}
