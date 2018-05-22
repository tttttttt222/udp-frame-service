package com.udp.frame.demo.service.handler;

import com.udp.frame.demo.common.ChannelMap;
import com.udp.frame.demo.common.PackageCacheMap;
import com.udp.frame.demo.dto.MsgPackage;
import com.udp.frame.demo.utils.FrameIncrease;
import com.udp.frame.demo.utils.MsgPackageUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 项目名称:testProject
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/7
 */
public class ServerHandler extends SimpleChannelInboundHandler<MsgPackage> {

    private static Logger logger = Logger.getLogger(ServerHandler.class);

    private FrameIncrease frameIncrease;


    private ExecutorService threadPool;

//    private ReentrantLock reentrantLock = new ReentrantLock();

    private volatile boolean isRunning = false;

    private Timer timer = new Timer(true);

//    private BlockingQueue<MsgPackage> bqueue = new LinkedBlockingQueue(10);

    public ServerHandler(FrameIncrease frameIncrease, ExecutorService threadPool) {
        this.frameIncrease = frameIncrease;
        this.threadPool = threadPool;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        threadPool.execute(new Runnable() {
//            public void run() {
//                try {
//                    MsgPackage msgPackage = bqueue.take();
//                    handlerMsgPackage(ctx, msgPackage);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    logger.error(e.getMessage());
//                }
//            }
//        });
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final MsgPackage msgPackage) {
//        logger.info(Thread.currentThread().getName() + "ServerHandler-接收到数据" + msgPackage);

        if (frameIncrease.getFrameNo() == msgPackage.getFrame() && !isRunning) {
            isRunning = true;
            //计时器
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    logger.info("广播执行-接收到包的发送者--" + PackageCacheMap.getInstance().getPMap() + "帧序号" + frameIncrease.getFrameNo());
                    try {
                        Map<String, InetSocketAddress> chmap = ChannelMap.getInstance().getChmap();
                        Map<String, MsgPackage> pMap = PackageCacheMap.getInstance().getPMap();
                        //所有数据接收到广播给所有人
                        Iterator<Map.Entry<String, InetSocketAddress>> iterator = chmap.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, InetSocketAddress> next = iterator.next();
                            String key = next.getKey();
//                            InetSocketAddress add = next.getValue();
                            MsgPackage msgPackageTmp = pMap.get(key);
                            if (msgPackageTmp != null) {
                                for (Object receiver : msgPackageTmp.getReceivers()) {
                                    msgPackageTmp.setAddress(chmap.get(receiver));
                                    ctx.writeAndFlush(msgPackageTmp);
                                }
                            }
                        }
                        pMap.clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(e.getMessage());
                    } finally {
                        frameIncrease.addFrameNo();
                        isRunning = false;
                    }
                }

            }, 10000);
        }


        handlerMsgPackage(ctx, msgPackage);

//        bqueue.add(msgPackage);


//        List<String> receivers = msgPackage.getReceivers();
//        for (String receiver : receivers) {
//            InetSocketAddress inetSocketAddress = chmap.get(receiver);
//            if (inetSocketAddress != null) {
//                //目标地址
//                msgPackage.setAddress(inetSocketAddress);
//                ch.writeAndFlush(msgPackage);
//            }
//        }
    }

    private void handlerMsgPackage(ChannelHandlerContext ctx, MsgPackage msgPackage) {
        Map<String, InetSocketAddress> chmap = ChannelMap.getInstance().getChmap();
        String sender = msgPackage.getSender();
        //判断帧
        if (frameIncrease.getFrameNo() == msgPackage.getFrame()) {
            MsgPackage copyMsgPackage = MsgPackageUtils.copyMsgPackage(msgPackage);
            //缓存一帧数据
            PackageCacheMap.getInstance().getPMap().put(sender, copyMsgPackage);
            msgPackage.setType(1);
            //发接收确认包,等待所有数据接收到
            msgPackage.setAddress(chmap.get(sender));
            ctx.writeAndFlush(msgPackage);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        logger.info("异常断开"+cause);
//        ctx.close();
    }
}
