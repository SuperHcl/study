package com.hcl.rpc.framework;


import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Hucl
 * @date: 2019/6/22 14:41
 * @description: 注册中心
 */
public class Registry {

    public static ConcurrentHashMap<String, Class> classMap;

    static {
        classMap = new ConcurrentHashMap<String, Class>();
    }
}
