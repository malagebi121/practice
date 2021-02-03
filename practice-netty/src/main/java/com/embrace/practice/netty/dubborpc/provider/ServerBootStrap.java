package com.embrace.practice.netty.dubborpc.provider;

import com.embrace.practice.netty.dubborpc.netty.NettyServer;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/10 23:11
 */
public class ServerBootStrap {
    public static void main(String[] args) {
        NettyServer.start("127.0.0.1",6700);
    }
}
