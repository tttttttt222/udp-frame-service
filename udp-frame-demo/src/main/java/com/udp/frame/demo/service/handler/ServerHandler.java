package com.udp.frame.demo.service.handler;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
import com.udp.frame.demo.common.ChannelMap;
import com.udp.frame.demo.common.PackageCacheMap;
import com.udp.frame.demo.dto.MsgPackage;
import com.udp.frame.demo.utils.FrameIncrease;
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

    private boolean isTimeout = false;

    private Timer timer = new Timer(true);

    public ServerHandler(FrameIncrease frameIncrease, ExecutorService threadPool) {
        this.frameIncrease = frameIncrease;
        this.threadPool = threadPool;
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ch, final MsgPackage msgPackage) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "ServerHandler-接收到数据" + msgPackage);

        if(frameIncrease.getFrameNo() == msgPackage.getFrame() && senderlist.isEmpty()){
            //计时器
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isTimeout =true;
                }
            },10);
        }


        threadPool.execute(new Runnable() {
            public void run() {
                Map<String, InetSocketAddress> chmap = ChannelMap.getInstance().getChmap();
                boolean isContain = false;
                String sender = msgPackage.getSender();
                //判断帧
                if (frameIncrease.getFrameNo() == msgPackage.getFrame() && !senderlist.contains(sender)) {
                    reentrantLock.lock();
                    senderlist.add(sender);
                    reentrantLock.unlock();
                    Set<String> keys = chmap.keySet();
                    for (String key : keys) {
                        if (senderlist.contains(key)) {
                            isContain = true;
                        } else {
                            isContain = false;
                            break;
                        }
                    }
                }


                if (isContain || isTimeout) {
                    //所有数据接收到广播给所有人
                    Iterator<Map.Entry<String, InetSocketAddress>> iterator = chmap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, InetSocketAddress> next = iterator.next();
                        String key = next.getKey();
//                            InetSocketAddress add = next.getValue();
                        MsgPackage msgPackage1 = PackageCacheMap.getInstance().getPMap().get(key);
                        for (Object receiver : msgPackage1.getReceivers()) {
                            msgPackage.setAddress(chmap.get(receiver));
                            ch.writeAndFlush(msgPackage1);
                        }
                    }
                    frameIncrease.addFrameNo();
                    senderlist.clear();
                } else {
                    //缓存一帧数据
                    PackageCacheMap.getInstance().getPMap().put(sender, msgPackage);
                    //发接收确认包,等待所有数据接收到
                    msgPackage.setAddress(chmap.get(sender));
                    ch.writeAndFlush(msgPackage);
                }
            }
        });


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
