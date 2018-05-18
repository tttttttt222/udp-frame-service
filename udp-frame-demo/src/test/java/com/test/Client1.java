package com.test;

import com.udp.frame.demo.client.NettyUdpClient;
import com.udp.frame.demo.client.ReceiveInfoInterface;
import com.udp.frame.demo.dto.request.SimpleFrameInfoRequest;
import com.udp.frame.demo.utils.FrameIncrease;

import java.net.InetSocketAddress;
import java.util.ArrayList;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/10
 */
public class Client1 {

    public static void main(String[] args) throws Exception {
        ArrayList<String> receivers = new ArrayList<String>();
        receivers.add("a");
        SimpleFrameInfoRequest simpleFrameInfoRequest = new SimpleFrameInfoRequest();
        FrameIncrease frameIncrease = new FrameIncrease();
        simpleFrameInfoRequest.setMsg("b连接");
        new NettyUdpClient<SimpleFrameInfoRequest>("b", receivers, new InetSocketAddress("127.0.0.1", 9999), simpleFrameInfoRequest, frameIncrease, new ReceiveInfoInterface() {
            public void readInfo(Object msg,int type) {
                simpleFrameInfoRequest.setMsg("b数据"+frameIncrease.getFrameNo());
                System.out.println(msg);
            }
        }).run();
    }

}
