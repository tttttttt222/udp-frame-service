package com.udp.frame.demo.utils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 项目名称:udp-frame-demo
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/10
 */
public class FrameIncreaseUtil {

    private static FrameIncreaseUtil frameIncreaseUtil;

    private AtomicLong frameNo = new AtomicLong(0);

    private FrameIncreaseUtil(){}

    public static FrameIncreaseUtil getInstance(){
        synchronized (FrameIncreaseUtil.class){
           if(frameIncreaseUtil == null){
               frameIncreaseUtil = new FrameIncreaseUtil();
           }
        }
        return frameIncreaseUtil;
    }

    public  AtomicLong getFrameNo() {
        return frameNo;
    }

    public void setFrameNo(long i) {
        this.frameNo =new AtomicLong(i);
    }

    public long addFrameNo(){
        return frameNo.incrementAndGet();
    }


}
