package com.lock;

import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * Created by on 2019/6/12.
 */
public class RedisSetNXTest {


    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + "主线程运行开始!");
        //更改key为a的值
        Jedis jedis=new Jedis("192.168.66.66",6379);
        jedis.set("goodsprice","0");
        System.out.println("输出初始化值："+jedis.get("goodsprice"));
        jedis.close();
        LockThread thread1 = new LockThread();
        LockThread thread2  = new LockThread();
        thread1.start();
        thread2.start();

        System.out.println(Thread.currentThread().getName() + "主线程运行结束!");
    }

}
