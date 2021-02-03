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
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author embrace
 * @describe  购买类测试
 *            不同版本下redis使用（被jmeter 坑惨了，工作组里面整了两个，会一起执行，所以老是超卖）
 *@method  buyVersion1  单机版无锁
 *
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
    private static final ReentrantLock lock = new ReentrantLock();

    //goods key
    private static final String GOODS_KEY = "goods";

    //redis  key
    private static final String REDIS_LOCK_KEY = "goods_lock";

    @Autowired
    private Redisson redisson;



    /**
     *  版本 1, 单机无锁版本
     */
    @GetMapping(value = "version1")
    public String buyVersion1(){
        String result = stringRedisTemplate.opsForValue().get(GOODS_KEY);//查询和修改不在一起，发生超卖
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
     *    打印虽然有重复，但是最终结果是对的，是打印的问题
     */
    @GetMapping(value = "version2")
    public String buyVersion2(){
//        synchronized (this){}  // syn
//        if(lock.tryLock()){   //或者等待时间 lock.tryLock(3, TimeUnit.SECONDS);
//            try{
//                lock.lock();
//            }finally {
//                lock.unlock();
//            }
//        }
        lock.lock();
//        if(lock.tryLock()){  // 怎么这个也不行，也不是 static 的问题吗
            try{
                //  这种和syn都是阻塞
                String result = stringRedisTemplate.opsForValue().get(GOODS_KEY);//查询和修改不在一起，发生超卖
                int goodsNum = result == null ? 0 : Integer.parseInt(result);
                if(goodsNum > 0){
                    int recentNum = goodsNum - 1;
                    stringRedisTemplate.opsForValue().set(GOODS_KEY, recentNum + "");
                    System.out.println("=====  " + recentNum + " =====");
                    log.info("你已经成功秒杀商品，此时还剩余：" + recentNum + "件"+"\t 服务器端口: "+serverPort);
                    return "你已经成功秒杀商品，此时还剩余：" + recentNum + "件"+"\t 服务器端口: "+serverPort;
                }
                log.info("商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort);
                return "商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort;
            }finally {
                //解锁的地方一定要对
                lock.unlock();
            }
//        }else{
//            return "商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort;
//        }
    }

    /**
     *  版本 3, 加入 redisLock
     *  解决了多服务器同步的问题
     *  但是还有很多问题（还是发生了超卖，这个是什么原因？）
     */
    @GetMapping(value = "version3")
    public String buyVersion3(){
        //加入锁value
        String value = IdUtil.simpleUUID() + ":" + Thread.currentThread().getName();
        boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK_KEY, value);//是对的，可能是当时服务有问题
        if(!flag)
            return  "没有抢到锁，重新提交试试！";
        String result = stringRedisTemplate.opsForValue().get(GOODS_KEY);
        int goodsNum = result == null ? 0 : Integer.parseInt(result);
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

    /**
     *  版本 4, 加入 try   finally
     *  解决了多服务器同步的问题
     *  这个单机都发生了超卖，是什么情况？ redis 版本的问题吗？
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
     *  版本 5, 加入锁的时间
     *  部署了微服务jar包的机器挂了，代码层面根本没有走到finally这块， 没办法保证解锁，这个key没有被删除，需要加入一个过期时间限定key
     *  set  REDIS_LOCK_KEY value ex 5  nx
     *  原子性设置key和过期时间
     *  boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK_KEY, value, 5L, TimeUnit.SECONDS);
     *  设置5秒过期
     */
    @GetMapping(value = "version5")
    public String buyVersion5(){
        //加入锁value
        String value = IdUtil.simpleUUID() + ":" + Thread.currentThread().getName();
        boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK_KEY, value, 5L, TimeUnit.SECONDS);//查询和修改不在一起，发生超卖
        if(!flag){
            log.info("没有抢到锁，重新提交试试！");
            return  "没有抢到锁，重新提交试试！";
        }else{
//            stringRedisTemplate.expire(REDIS_LOCK_KEY, 5L, TimeUnit.SECONDS);  // 这样不行，没有原子性
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
     *  版本6，业务超时可能删除别人的锁，造成张冠李戴，发生错误
     *  解决方法：只能删除自己的锁，不能删除别人的
     */
    @GetMapping(value = "version6")
    public String buyVersion6(){
        //加入锁value
        String value = IdUtil.simpleUUID() + ":" + Thread.currentThread().getName();
        boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK_KEY, value, 5L, TimeUnit.SECONDS);//查询和修改不在一起，发生超卖
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
                //业务代码超时，自己的锁已经失效，别人加上了锁，自己去把别人的锁删来丢了，造成严重后果
                //自己是能删除自己的锁
                if(value.equalsIgnoreCase(stringRedisTemplate.opsForValue().get(REDIS_LOCK_KEY))){
                    stringRedisTemplate.delete(REDIS_LOCK_KEY);
                }
            }
        }
    }

    /**
     *  版本7 ， 在 finally 释放锁中，可能判断与删除没有原子性，可能不是同一客户端
     *  解决办法，上事务  或者 lua 脚本
     */
    @GetMapping(value = "version7")
    public String buyVersion7(){
        //加入锁value
        String value = IdUtil.simpleUUID() + ":" + Thread.currentThread().getName();
        boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK_KEY, value, 5L, TimeUnit.SECONDS);//查询和修改不在一起，发生超卖
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
                // redis 事务的乐观锁
                while (true)
                {
                    stringRedisTemplate.watch(REDIS_LOCK_KEY); //加事务，乐观锁
                    if (value.equalsIgnoreCase(stringRedisTemplate.opsForValue().get(REDIS_LOCK_KEY))){
                        stringRedisTemplate.setEnableTransactionSupport(true);//打开事务设置
                        stringRedisTemplate.multi();//开始事务
                        stringRedisTemplate.delete(REDIS_LOCK_KEY);
                        List<Object> list = stringRedisTemplate.exec();
                        if (list == null) {  //如果等于null，就是没有删掉，删除失败，再回去while循环那再重新执行删除
                            continue;
                        }
                    }
                    //如果删除成功，释放监控器，并且breank跳出当前循环
                    stringRedisTemplate.unwatch();
                    break;
                }
            }
        }
    }

    /**
     *  版本8 ， 在 finally 释放锁中，可能判断与删除没有原子性，在redis集群下可能不是同一客户端
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
                //官网
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
        String value = IdUtil.simpleUUID() + ":" + Thread.currentThread().getName();
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
