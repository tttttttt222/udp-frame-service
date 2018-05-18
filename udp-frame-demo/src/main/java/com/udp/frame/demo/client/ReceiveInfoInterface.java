package com.udp.frame.demo.client;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/10
 */
public interface ReceiveInfoInterface<T> {

    void readInfo(T msg,int type);
}
