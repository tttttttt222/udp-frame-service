package com.udp.frame.demo.client.handler;

import com.udp.frame.demo.client.ReceiveInfoInterface;
import com.udp.frame.demo.dto.MsgPackage;
import com.udp.frame.demo.utils.FrameIncrease;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.Timer;


/**
 * 项目名称:testProject
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/8
 */
public class ClientCoreHandler<T> extends SimpleChannelInboundHandler<MsgPackage> {

    private static Logger logger = Logger.getLogger(ClientCoreHandler.class);

    private ReceiveInfoInterface receiveInfoInterface;

    private Timer timer;

    private long timeout;

    private MsgPackage msgPackage;

    private FrameIncrease frameIncrease;

    public ClientCoreHandler(Timer timer, long timeout, FrameIncrease frameIncrease, MsgPackage<T> msgPackage, ReceiveInfoInterface receiveInfoInterface) {
        this.timer = timer;
        this.timeout = timeout;
        this.frameIncrease = frameIncrease;
        this.msgPackage=msgPackage;
        this.receiveInfoInterface = receiveInfoInterface;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        msgPackage.setSeq(System.currentTimeMillis());
        msgPackage.setFrame(frameIncrease.getFrameNo());
        msgPackage.setType(1);
        frameIncrease.addFrameNo();
        logger.info("ClientCoreHandler-连接-发送数据:" + msgPackage);
        ctx.writeAndFlush(msgPackage);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgPackage msgReceivePackage) throws Exception {

        switch (msgReceivePackage.getType()) {
            case 0:
                logger.info("接收到-普通-数据:" + msgReceivePackage);
                receiveInfoInterface.readInfo(msgReceivePackage.getInfo(),0);
                //发送下一帧数据
                sendNormalFramePackage(ctx, msgPackage);
                break;
            case 1:
                logger.info("接收到-应答-数据:" + msgReceivePackage);
                if(msgReceivePackage.getFrame() == 0L){ //连接确认包
                    receiveInfoInterface.readInfo(msgReceivePackage.getInfo(),1);
                    //发送第一帧数据
                    sendNormalFramePackage(ctx, msgPackage);
                }
                //收确认包,规定时间内没有收到,断线重连
                ;
                break;
            default:
                ;
        }

    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("异常断开" + cause);
        ctx.close();
    }

    private void sendNormalFramePackage(ChannelHandlerContext ctx, MsgPackage msgPackage) {
        msgPackage.setType(0);
        msgPackage.setSeq(System.currentTimeMillis());
        msgPackage.setFrame(frameIncrease.getFrameNo());
        frameIncrease.addFrameNo();
        logger.info("ClientCoreHandler-普通-发送数据:" + msgPackage);
        ctx.writeAndFlush(msgPackage);
    }


}
