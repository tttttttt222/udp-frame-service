package com.udp.frame.demo.utils;

import com.udp.frame.demo.dto.MsgPackage;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/18
 */
public class MsgPackageUtils {

    public static <T> MsgPackage<T> createMsgPackage(String sender, List<String> receivers, InetSocketAddress serverAddress,T data){
        //连接包
        MsgPackage<T> msgPackage = new MsgPackage<T>();
        msgPackage.setSender(sender);
        msgPackage.setAddress(serverAddress);
        msgPackage.setReceivers(receivers);

        msgPackage.setInfo(data);

        return msgPackage;
    }

    public static <T> MsgPackage<T> copyMsgPackage(MsgPackage<T> msgPackage){
        MsgPackage<T> objectMsgPackage = new MsgPackage<>();
        objectMsgPackage.setType(msgPackage.getType());
        objectMsgPackage.setSeq(msgPackage.getSeq());
        objectMsgPackage.setFrame(msgPackage.getFrame());
        objectMsgPackage.setAddress(msgPackage.getAddress());
        objectMsgPackage.setSender(msgPackage.getSender());
        objectMsgPackage.setReceivers(msgPackage.getReceivers());
        objectMsgPackage.setInfo(msgPackage.getInfo());
        return objectMsgPackage;
    }


}
