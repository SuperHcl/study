package com.lock;

/**
 * Created by on 2019/6/13.
 */
public class RedissonDemo {

    public static void main(String[] args) {

        //模拟多线程
        for(int i=0;i<20;i++){
            //创建线程
            LockRessionThread th = new LockRessionThread();
            th.start();

        }
    }


}
