package com.lock;



/**
 * Created by on 2019/6/13.
 */
public class LockRessionThread extends Thread {

    @Override
    public void run() {
        //获取锁
        boolean isOK = RedissonDistributedLock.acquire("lock_" + Thread.currentThread().getName());

        //

        //判断
        if(isOK){

        }

    }
}
