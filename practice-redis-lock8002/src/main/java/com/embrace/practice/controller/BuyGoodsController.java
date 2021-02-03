package com.embrace.practice.controller;

import cn.hutool.core.util.IdUtil;
import com.embrace.practice.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author embrace
 * @describe  购买类测试
 * @date created in 2020/12/14 10:12
 */
@RestController
@RequestMapping("buy")
@Slf4j
public class BuyGoodsController {

    @Autowired
    private  StringRedisTemplate stringRedisTemplate;

    @Value("${server.port}")
    public String serverPort;

    //重入锁
    private static ReentrantLock lock = new ReentrantLock();

    //goods key
    private static final String GOODS_KEY = "goods";

    //redis  key
    private static final String REDIS_LOCK_KEY = "goods_lock";

    @Autowired
    private Redisson redisson;

    /**
     *  版本 1, 无锁版本
     * @return
     */
    @GetMapping(value =  "version1")
    public String buyVersion1(){
        String result = stringRedisTemplate.opsForValue().get("goods");
        int goodsNum = result == null ? 0 : Integer.parseInt(result);
        if(goodsNum > 0){
            int recentNum = goodsNum - 1;
            stringRedisTemplate.opsForValue().set("goods", recentNum + "");
            log.info("你已经成功秒杀商品，此时还剩余：" + recentNum + "件"+"\t 服务器端口: "+serverPort);
            return "你已经成功秒杀商品，此时还剩余：" + recentNum + "件"+"\t 服务器端口: "+serverPort;
        }
        log.info("商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort);
        return "商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort;
    }

    /**
     *  版本 2, 单机有锁版本，用阻塞模式能解决单机多线程同步，但是多台机子下不行
     *    使用 ReentrantLock 或者  synchronized
     *    在jmeter下面肉眼可见的慢
     */
    @GetMapping(value = "version2")
    public String buyVersion2(){
//        synchronized (this){}

//        if(lock.tryLock()){   //或者等待时间 lock.tryLock(3, TimeUnit.SECONDS);
//            try{
//                lock.lock();
//            }finally {
//                lock.unlock();
//            }
//        }
        lock.lock(); //这个好像不行啊
        try{
            //  这种和syn都是阻塞
            String result = stringRedisTemplate.opsForValue().get("goods");//查询和修改不在一起，发生超卖
            int goodsNum = result == null ? 0 : Integer.parseInt(result);
            if(goodsNum > 0){
                int recentNum = goodsNum - 1;
                stringRedisTemplate.opsForValue().set("goods", recentNum + "");
                log.info("你已经成功秒杀商品，此时还剩余：" + recentNum + "件"+"\t 服务器端口: "+serverPort);
                return "你已经成功秒杀商品，此时还剩余：" + recentNum + "件"+"\t 服务器端口: "+serverPort;
            }
            log.info("商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort);
            return "商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort;
        }finally {
            lock.unlock();
        }
    }

    /**
     *  版本 3, 加入 redisLock
     *  解决了多服务器同步的问题
     *  但是还有很多问题
     *  严重：这个并发测试出来还有问题，不知道为什么
     */
    @GetMapping(value = "version3")
    public String buyVersion3(){
        //加入锁value
        String value = IdUtil.simpleUUID() + ":" + Thread.currentThread().getName();
        boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK_KEY, value);//查询和修改不在一起，发生超卖
        if(!flag){
            log.info("没有抢到锁，重新提交试试！");
            return  "没有抢到锁，重新提交试试！";
        }else{
            String result = stringRedisTemplate.opsForValue().get(GOODS_KEY);
            int goodsNum = result == null ? 0 : Integer.parseInt(result);
            System.out.println("=======" + goodsNum + " ======= ");
            if(goodsNum > 0){
                int recentNum = goodsNum - 1;
                stringRedisTemplate.opsForValue().set("goods", recentNum + "");
                //释放锁
                stringRedisTemplate.delete(REDIS_LOCK_KEY);
                log.info("你已经成功秒杀商品，此时还剩余：" + recentNum + "件"+"\t 服务器端口: "+serverPort);
                return "你已经成功秒杀商品，此时还剩余：" + recentNum + "件"+"\t 服务器端口: "+serverPort;
            }
            //释放锁
            stringRedisTemplate.delete(REDIS_LOCK_KEY);
            log.info("商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort);
            return "商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort;
        }
    }

    /**
     *  版本 4, 加入 try   finally
     *  解决了多服务器同步的问题
     *  但是还有很多问题
     */
    @GetMapping(value = "version4")
    public String buyVersion4(){
        //加入锁value
        String value = IdUtil.simpleUUID() + ":" + Thread.currentThread().getName();
        boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK_KEY, value);//查询和修改不在一起，发生超卖
        if(!flag){
            log.info("没有抢到锁，重新提交试试！");
            return  "没有抢到锁，重新提交试试！";
        }else{
            try{
                String result = stringRedisTemplate.opsForValue().get(GOODS_KEY);
                int goodsNum = result == null ? 0 : Integer.parseInt(result);
                if(goodsNum > 0){
                    int recentNum = goodsNum - 1;
                    //可能先解锁了还没有设置测成功
                    stringRedisTemplate.opsForValue().set(GOODS_KEY, recentNum + "");
                    log.info("你已经成功秒杀商品，此时还剩余：" + recentNum + "件"+"\t 服务器端口: "+serverPort);
                    return "你已经成功秒杀商品，此时还剩余：" + recentNum + "件"+"\t 服务器端口: "+serverPort;
                }
                log.info("商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort);
                return "商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort;
            }finally {
                stringRedisTemplate.delete(REDIS_LOCK_KEY);
            }
        }
    }

    /**
     *  版本8 ， 在 finally 释放锁中，可能判断与删除没有原子性，可能不是同一客户端
     *  解决办法，上事务  或者 lua 脚本（jedis 的 eval ）
     */
    @GetMapping(value = "version8")
    public String buyVersion8(){
        //加入锁value
        String value = IdUtil.simpleUUID() + ":" + Thread.currentThread().getName();
        boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK_KEY, value, 5L, TimeUnit.SECONDS);//查询和修改不在一起，发生超卖
        if(!flag){
//            log.info("没有抢到锁，重新提交试试！");
            return  "没有抢到锁，重新提交试试！";
        }else{
            try{
                String result = stringRedisTemplate.opsForValue().get(GOODS_KEY);
                int goodsNum = result == null ? 0 : Integer.parseInt(result);
                if(goodsNum > 0){
                    int recentNum = goodsNum - 1;
                    //可能先解锁了还没有设置测成功
                    stringRedisTemplate.opsForValue().set(GOODS_KEY, recentNum + "");
                    log.info("你已经成功秒杀商品，此时还剩余：" + recentNum + "件"+"\t 服务器端口: "+serverPort);
                    return "你已经成功秒杀商品，此时还剩余：" + recentNum + "件"+"\t 服务器端口: "+serverPort;
                }
                log.info("商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort);
                return "商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort;
            }finally {
                Jedis jedis = null;
                try {
                    jedis = RedisUtils.getJedis();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String script = "if redis.call('get', KEYS[1]) == ARGV[1]"+"then "
                        +"return redis.call('del', KEYS[1])"+"else "+ "  return 0 " + "end";
                try{
                    Object result = jedis.eval(script, Collections.singletonList(REDIS_LOCK_KEY), Collections.singletonList(value));
                    if ("1".equals(result.toString())){
                        System.out.println("------del REDIS_LOCK_KEY success");
                    }else {
                        System.out.println("------del REDIS_LOCK_KEY error");
                    }
                }finally {
                    if (null != jedis){
                        jedis.close();
                    }
                }
            }
        }
    }

    /**
     *  版本9 ， 以前的还是没有解决业务代码执行大于 redisKey 的时候 ， redis 的缓存续期问题？
     *  直接上redisson
     */
    @GetMapping(value = "version9")
    public String buyVersion9(){
        //加入锁value
//        String value = IdUtil.simpleUUID() + ":" + Thread.currentThread().getName();
        //这个就暴力了
        RLock lock = redisson.getLock(REDIS_LOCK_KEY);
        lock.lock();
        try{
            String result = stringRedisTemplate.opsForValue().get(GOODS_KEY);
            int goodsNum = result == null ? 0 : Integer.parseInt(result);
            if(goodsNum > 0){
                int recentNum = goodsNum - 1;
                //可能先解锁了还没有设置测成功
                stringRedisTemplate.opsForValue().set(GOODS_KEY, recentNum + "");
                log.info("你已经成功秒杀商品，此时还剩余：" + recentNum + "件"+"\t 服务器端口: "+serverPort);
                return "你已经成功秒杀商品，此时还剩余：" + recentNum + "件"+"\t 服务器端口: "+serverPort;
            }
            log.info("商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort);
            return "商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort;
        }finally {
            //            lock.unlock();   //直接解锁在超高并发中可能存在问题
            if(lock.isLocked() && lock.isHeldByCurrentThread()){
                //是锁住，并且当前线程持有锁
                lock.unlock();
            }
        }
    }





}
