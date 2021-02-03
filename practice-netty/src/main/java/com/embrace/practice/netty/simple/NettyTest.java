package com.embrace.practice.netty.simple;

import io.netty.channel.DefaultSelectStrategyFactory;
import io.netty.util.NettyRuntime;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/6 21:19
 */
public class NettyTest {
    public static void main(String[] args) {
        // 8核，为什么不是 16 呢
        System.out.println(NettyRuntime.availableProcessors());
    }
}
