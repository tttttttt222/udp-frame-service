package com.udp.frame.demo.dto.request;


import java.util.List;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/10
 */
public class SimpleFrameInfoRequest {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "SimpleFrameInfoRequest{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
