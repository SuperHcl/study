package com.hcl.rpc.framework;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author: Hucl
 * @date: 2019/6/22 15:03
 * @description:
 */
public class RemoteInvocationHandler implements InvocationHandler {
    private String className;

    private String host;

    private int port;

    public RemoteInvocationHandler(String className, String host, int port) {
        this.className = className;
        this.host = host;
        this.port = port;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 获取连接
        Socket socket = new Socket(host, port);
        // 封装消息体
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(className);
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);
        rpcRequest.setType(method.getParameterTypes());

        // 将消息体发送到服务端
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        Object result = null;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(rpcRequest);

            // 服务端获取返回结果
            inputStream = new ObjectInputStream(socket.getInputStream());
            result = inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                if (inputStream!=null) {
                    inputStream.close();
                }
                if (outputStream!=null) {
                    outputStream.close();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
