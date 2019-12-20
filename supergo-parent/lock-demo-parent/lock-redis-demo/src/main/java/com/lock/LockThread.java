package com.lock;

import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * Created by on 2019/6/12.
 */
public class LockThread extends Thread {


    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";


    private static int bidPrice = 100;

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "线程运行开始 ");
        Jedis jedis=new Jedis("192.168.66.66",6379);
        try {
            //拿到锁
            boolean isOk=  tryGetDistributedLock(jedis, "goods_lock", Thread.currentThread().getName() , 10000);


            //可重入锁问题
            if(!isOk){
                //判断当前线程的名称，和之前线程名称是否相等，如果相等，那就是可重入锁
                if(jedis.get("goods_lock").equals(Thread.currentThread().getName())){

                }
            }

            //判断获取锁是否成功
            if(isOk) {
                System.out.println("子线程"+Thread.currentThread().getName() +"拿到锁");
                String v =  jedis.get("goodsprice");
                Integer iv = Integer.valueOf(v);

                //============== 业务代码块start ==================
                //条件都给过
                if(bidPrice > iv){

                    Integer bp = iv + 100;
                    //出价成功，事务未提交
                    jedis.set("goodsprice",String.valueOf(bp));
                    System.out.println("子线程"+Thread.currentThread().getName() +", 出价："+ jedis.get("goodsprice") +"，出价时间："
                            + System.nanoTime());

                }else{
                    System.out.println("出价低于现有价格！");
                }

                //============== 业务代码块end ==================



                //判断当前还没有执行完毕，

                //==============================================

                //释放锁
                boolean isOk1=  releaseDistributedLock(jedis, "goods_lock", Thread.currentThread().getName());
                if(isOk1){
                    System.out.println("子线程"+Thread.currentThread().getName() +"释放锁");
                }

            }else{

                System.out.println("子线程" + Thread.currentThread().getName() + "未拿到锁");
            }
            jedis.close();
            System.out.println(Thread.currentThread().getName() + "线程运行结束");

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }finally {
            //确保代码出现异常，也能释放锁，让其他机器线程继续执行
            //释放锁
            boolean isOk1=  releaseDistributedLock(jedis, "goods_lock", Thread.currentThread().getName());
        }
    }


    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {

        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }

    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public  boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections
                .singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }

}
