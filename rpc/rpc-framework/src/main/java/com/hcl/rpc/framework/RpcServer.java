package com.hcl.rpc.framework;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Hucl
 * @date: 2019/6/22 14:31
 * @description:
 */
public class RpcServer {
    ExecutorService executorService = Executors.newFixedThreadPool(20);

    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                // 阻塞
                final Socket socket = serverSocket.accept();
                executorService.execute(new ProcessorHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
