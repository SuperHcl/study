package com.hcl.rpc.framework;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author: Hucl
 * @date: 2019/6/22 14:35
 * @description:
 */
public class ProcessorHandler implements Runnable {
    private Socket socket;
    public ProcessorHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        // 接受客户端的请求，解析消息体的数据
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {
            ois = new ObjectInputStream(socket.getInputStream());
            RpcRequest rpcRequest = (RpcRequest) ois.readObject();
            // 从注册中心获取
            Class clazz = null;
            if (Registry.classMap.containsKey(rpcRequest.getClassName())) {
                clazz = Registry.classMap.get(rpcRequest.getClassName());
            } else {
                throw new RuntimeException("no such class. your class name is "+rpcRequest.getClassName());

            }
            // 通过反射调用本地方法
            Method method = clazz.getMethod(rpcRequest.getMethodName(), rpcRequest.getType());
            Object result = method.invoke(clazz.newInstance(), rpcRequest.getParameters());

            // 返回结果给客户端
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(result);
            oos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                if (ois!=null) {
                    ois.close();
                }
                if (oos!=null) {
                    oos.close();
                }
                if (socket!=null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
