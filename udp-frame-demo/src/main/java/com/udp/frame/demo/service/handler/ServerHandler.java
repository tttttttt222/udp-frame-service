package com.udp.frame.demo.service.handler;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
import com.udp.frame.demo.common.ChannelMap;
import com.udp.frame.demo.common.PackageCacheMap;
import com.udp.frame.demo.dto.MsgPackage;
import com.udp.frame.demo.utils.FrameIncrease;
import com.udp.frame.demo.utils.MsgPackageUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 项目名称:testProject
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/7
 */
public class ServerHandler extends SimpleChannelInboundHandler<MsgPackage> {

    private FrameIncrease frameIncrease;

    //已经发送数据的人
    private List<String> senderlist = new ArrayList<String>();

    private ExecutorService threadPool;

    private ReentrantLock reentrantLock = new ReentrantLock();

//    private boolean isTimeout = false;

    private Timer timer = new Timer(true);

    public ServerHandler(FrameIncrease frameIncrease, ExecutorService threadPool) {
        this.frameIncrease = frameIncrease;
        this.threadPool = threadPool;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ch, final MsgPackage msgPackage) {
//        System.out.println(Thread.currentThread().getName() + "ServerHandler-接收到数据" + msgPackage);

        if (frameIncrease.getFrameNo() == msgPackage.getFrame() && senderlist.isEmpty() && msgPackage.getType() == 0) {
            //计时器
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("广播执行-接收到包的发送者数量--" + senderlist.size() + "--" + senderlist + "帧序号" + frameIncrease.getFrameNo());
                    Map<String, InetSocketAddress> chmap = ChannelMap.getInstance().getChmap();
                    //所有数据接收到广播给所有人
                    Iterator<Map.Entry<String, InetSocketAddress>> iterator = chmap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, InetSocketAddress> next = iterator.next();
                        String key = next.getKey();
//                            InetSocketAddress add = next.getValue();
                        MsgPackage msgPackageTmp = PackageCacheMap.getInstance().getPMap().get(key);
                        if (msgPackageTmp != null) {
                            for (Object receiver : msgPackageTmp.getReceivers()) {
                                msgPackageTmp.setAddress(chmap.get(receiver));
                                try {
                                    ch.writeAndFlush(msgPackageTmp).await();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    frameIncrease.addFrameNo();
                    senderlist.clear();
                }

            }, 10000);
        }


//        threadPool.execute(new Runnable() {
//            public void run() {
        Map<String, InetSocketAddress> chmap = ChannelMap.getInstance().getChmap();
        String sender = msgPackage.getSender();
        //判断帧
        if (frameIncrease.getFrameNo() == msgPackage.getFrame() && !senderlist.contains(sender)) {
            senderlist.add(sender);
            MsgPackage copyMsgPackage = MsgPackageUtils.copyMsgPackage(msgPackage);
            //缓存一帧数据
            PackageCacheMap.getInstance().getPMap().put(sender, copyMsgPackage);
            msgPackage.setType(1);
            //发接收确认包,等待所有数据接收到
            msgPackage.setAddress(chmap.get(sender));
            ch.writeAndFlush(msgPackage);
        }
//            }
//        });


//         boolean isContain = false;
//                    Set<String> keys = chmap.keySet();
//                    for (String key : keys) {
//                        if (senderlist.contains(key)) {
//                            isContain = true;
//                        } else {
//                            isContain = false;
//                            break;
//                        }
//                    }

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

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        System.out.println("异常断开"+cause);
//        ctx.close();
    }
}
