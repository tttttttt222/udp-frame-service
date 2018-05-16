package com.udp.frame.demo.common;

import com.udp.frame.demo.dto.MsgPackage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目名称:producer
 * 描述:
 * 创建人:ryw
 * 创建时间:2017/11/3
 */
public class PackageCacheMap {

    private static PackageCacheMap packageCacheMap;

    private Map<String,MsgPackage> pMap = new ConcurrentHashMap<String, MsgPackage>();

    private PackageCacheMap() { }

    public static PackageCacheMap getInstance() {
        synchronized (PackageCacheMap.class){
            if (packageCacheMap == null) {
                packageCacheMap = new PackageCacheMap();
            }
        }
        return packageCacheMap;
    }

    public Map<String, MsgPackage> getPMap() {
        return pMap;
    }

}
