package com.embrace.practice.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author embrace
 * @describe redisUtil, 使用jedis连接池
 * @date created in 2020/12/16 19:05
 */
public class RedisUtils {
    private static JedisPool jedisPool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);

        jedisPool = new JedisPool(jedisPoolConfig,"tianfudadao.cn",6379,100000,"123456");
    }

    public static Jedis getJedis() throws Exception{
        if (null!=jedisPool){
            return jedisPool.getResource();
        }
        throw new Exception("Jedispool is not ok");
    }

}
