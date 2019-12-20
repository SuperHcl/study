package com.lock;

import com.lock.impl.DistributedLock;
import org.I0Itec.zkclient.ZkClient;

/**
 * Created by on 2019/6/14.
 */
public class lockApi {

    public static void main(String[] args) throws Exception {
        //
        ZkClient zkClient = new ZkClient("192.168.66.66:2181", 3000);
        DistributedLock simple = new DistributedLock(zkClient, "/locker");

        for (int i = 0; i < 10; i++) {
            try {
                simple.acquire();
                System.out.println("正在进行运算操作：" + System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                simple.release();
                System.out.println("=================\r\n");
            }
        }
    }

}
