package com.udp.frame.demo.dto;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/9
 */
public class MsgPackage<T> {


    /*0 普通上传 1连接包 2 重连*/
    int type;

    /*请求序列*/
    long seq;

    /*帧序号*/
    long frame;

    private InetSocketAddress address;

    private String sender;

    private List<String> receivers;

    /*数据*/
    T info;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public long getFrame() {
        return frame;
    }

    public void setFrame(long frame) {
        this.frame = frame;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "MsgPackage{" +
                "type=" + type +
                ", seq=" + seq +
                ", frame=" + frame +
                ", address=" + address +
                ", sender='" + sender + '\'' +
                ", receivers=" + receivers +
                ", info=" + info +
                '}';
    }
}
