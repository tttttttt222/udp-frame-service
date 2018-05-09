package com.udp.frame.demo.dto;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/9
 */
public class MsgPackage {


    /*数据包长度*/
    int length;

    /*0 普通上传 1 重连*/
    int type;


    /*请求序列*/
    int seq;

    /*帧序号*/
    int frame;

    /*数据json*/
    String msg;


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
