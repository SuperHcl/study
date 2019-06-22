package com.hcl.rpc.framework;


import java.lang.reflect.Proxy;

/**
 * @author: Hucl
 * @date: 2019/6/22 14:59
 * @description: 代理
 */
public class RpcProxy<T> {


    public T remoteCall(Class<T> interfaceCls, String host, int port) {
        try {
            return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(), new Class[]{interfaceCls},
                    new RemoteInvocationHandler(interfaceCls.getName(), host, port));
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;

    }
}
