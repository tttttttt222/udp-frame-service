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
public class Clinet2 {


    public static void main(String[] args) throws Exception {
        ArrayList<String> receivers = new ArrayList<String>();
        receivers.add("b");
        SimpleFrameInfoRequest simpleFrameInfoRequest = new SimpleFrameInfoRequest();
        FrameIncrease frameIncrease = new FrameIncrease();
        simpleFrameInfoRequest.setMsg("第" + frameIncrease.getFrameNo() + "数据");
        new NettyUdpClient<SimpleFrameInfoRequest>("a", receivers, new InetSocketAddress("127.0.0.1", 9999), simpleFrameInfoRequest, frameIncrease, new ReceiveInfoInterface() {
            public void readInfo(Object msg) {
                System.out.println(msg);
            }
        }).run();
    }

}
