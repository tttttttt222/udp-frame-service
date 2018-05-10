package com.udp.frame.demo.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/10
 */
public class FrameIncrease {

//    private static FrameIncrease frameIncreaseUtil;

    private AtomicLong frameNo = new AtomicLong(0);

//    private FrameIncrease(){}
//
//    public static FrameIncrease getInstance(){
//        synchronized (FrameIncrease.class){
//           if(frameIncreaseUtil == null){
//               frameIncreaseUtil = new FrameIncrease();
//           }
//        }
//        return frameIncreaseUtil;
//    }

    public  long getFrameNo() {
        return frameNo.get();
    }

    public void setFrameNo(long i) {
        this.frameNo =new AtomicLong(i);
    }

    public long addFrameNo(){
        return frameNo.incrementAndGet();
    }


}
