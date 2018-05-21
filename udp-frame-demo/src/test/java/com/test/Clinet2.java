package com.test;

import com.udp.frame.demo.client.NettyUdpClient;
import com.udp.frame.demo.client.ReceiveInfoInterface;
import com.udp.frame.demo.dto.request.SimpleFrameInfoRequest;
import com.udp.frame.demo.utils.FrameIncrease;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.ArrayList;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/10
 */
public class Clinet2 {

    private static Logger logger = Logger.getLogger(Clinet2.class);

    public static void main(String[] args) throws Exception {
        ArrayList<String> receivers = new ArrayList<String>();
        receivers.add("b");
        final SimpleFrameInfoRequest simpleFrameInfoRequest = new SimpleFrameInfoRequest();
        FrameIncrease frameIncrease = new FrameIncrease();
        simpleFrameInfoRequest.setMsg("a连接");
        new NettyUdpClient<SimpleFrameInfoRequest>("a", receivers, new InetSocketAddress("127.0.0.1", 9999), simpleFrameInfoRequest, frameIncrease, new ReceiveInfoInterface() {
            public void readInfo(Object msg,int type) {
                simpleFrameInfoRequest.setMsg("a数据"+frameIncrease.getFrameNo());
                System.out.println(msg);
            }
        }).run();
    }

    @Test
    public void logtest(){
        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");
    }

}
