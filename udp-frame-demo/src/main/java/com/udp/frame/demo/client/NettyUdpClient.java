package com.udp.frame.demo.client;

import com.udp.frame.demo.client.handler.ClientCoreHandler;
import com.udp.frame.demo.abandon.ClientActiveHandler;
import com.udp.frame.demo.common.InfoDecodeHandler;
import com.udp.frame.demo.common.InfoEncodeHandler;
import com.udp.frame.demo.utils.FrameIncrease;
import com.udp.frame.demo.utils.MsgPackageUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;


import java.net.InetSocketAddress;
import java.util.List;
import java.util.Timer;

/**
 * 项目名称:testProject
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/7
 */
public class NettyUdpClient<T> {

    private Timer timer = new Timer(true);

    private String sender;

    private List<String> receivers;

    private InetSocketAddress serverAddress;

    private T data;

    private ReceiveInfoInterface receiveInfoInterface;

    private FrameIncrease frameIncrease;

    public NettyUdpClient(String sender, List<String> receivers, InetSocketAddress serverAddress, T data, FrameIncrease frameIncrease, ReceiveInfoInterface receiveInfoInterface) {
        this.sender = sender;
        this.receivers = receivers;
        this.serverAddress = serverAddress;
        this.data = data;
        this.receiveInfoInterface = receiveInfoInterface;
        this.frameIncrease = frameIncrease;
    }

    public void run() throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            //由于我们用的是UDP协议，所以要用NioDatagramChannel来创建
            b.group(workerGroup).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_RCVBUF, 1024 * 1024)// 设置UDP读缓冲区为1M
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024)// 设置UDP写缓冲区为1M
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                        @Override
                        protected void initChannel(NioDatagramChannel nioDatagramChannel) throws Exception {
                            nioDatagramChannel.pipeline().addLast(new InfoEncodeHandler());
                            nioDatagramChannel.pipeline().addLast(new InfoDecodeHandler());
//                            nioDatagramChannel.pipeline().addLast(new ClientActiveHandler(timer, 3000, frameIncrease,MsgPackageUtils.createMsgPackage(sender, receivers, serverAddress, 1,data)));
                            nioDatagramChannel.pipeline().addLast(new ClientCoreHandler(timer,3000,frameIncrease,MsgPackageUtils.createMsgPackage(sender, receivers, serverAddress, data),receiveInfoInterface));
                        }
                    });
            ChannelFuture future = b.bind(0).sync();
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }


//    public static void main(String[] args) throws Exception {
//        ArrayList<String> receivers = new ArrayList<String>();
//        receivers.add("b");
//        SimpleFrameInfoRequest simpleFrameInfoRequest = new SimpleFrameInfoRequest();
//        simpleFrameInfoRequest.setMsg("第数据");
//        new NettyUdpClient<SimpleFrameInfoRequest>("a", receivers, new InetSocketAddress("127.0.0.1", 9999), simpleFrameInfoRequest, new FrameIncrease(), new ReceiveInfoInterface() {
//            public void readInfo(Object msg) {
//                System.out.println(msg);
//            }
//        }).run();
//    }
}
