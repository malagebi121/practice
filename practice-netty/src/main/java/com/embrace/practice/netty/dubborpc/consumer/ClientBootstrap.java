package com.embrace.practice.netty.dubborpc.consumer;

import com.embrace.practice.netty.dubborpc.netty.NettyClient;
import com.embrace.practice.netty.dubborpc.service.HelloService;
import jdk.nashorn.internal.ir.CallNode;

import java.util.concurrent.TimeUnit;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/11 0:08
 */
public class ClientBootstrap {
    public static String providerName = "service#hello#";

    public static void main(String[] args) throws InterruptedException {
        NettyClient consumer = new NettyClient();
        HelloService helloService = (HelloService) consumer.getBean(HelloService.class, providerName);
        for (int i = 0; i < 20; i++) {
//            System.out.println(helloService);  // 代理对象为什么是null
            String res = helloService.hello("你是个小傻瓜");
            System.out.println("服务器端返回结果:" + res);
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
