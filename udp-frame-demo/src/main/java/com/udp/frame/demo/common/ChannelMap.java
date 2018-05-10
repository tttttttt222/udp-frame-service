package com.udp.frame.demo.common;

import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目名称:producer
 * 描述:
 * 创建人:ryw
 * 创建时间:2017/11/3
 */
public class ChannelMap {

    private static  ChannelMap channelMap;

    private Map<String,InetSocketAddress> chmap = new ConcurrentHashMap<String, InetSocketAddress>();

    private ChannelMap() { }

    public static ChannelMap getInstance() {
        synchronized (ChannelMap.class){
            if (channelMap == null) {
                channelMap = new ChannelMap();
            }
        }
        return channelMap;
    }

    public Map<String, InetSocketAddress> getChmap() {
        return chmap;
    }

}
