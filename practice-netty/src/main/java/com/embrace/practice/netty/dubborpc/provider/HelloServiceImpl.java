package com.embrace.practice.netty.dubborpc.provider;

import com.embrace.practice.netty.dubborpc.service.HelloService;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/10 22:46
 */
public class HelloServiceImpl  implements HelloService {
    private AtomicInteger integer = new AtomicInteger(0);

    @Override
    public String hello(String message) {
        // 为什么代理的 service 每次都不一样呢
        System.out.println("serviceImpl  调用的 ： "  +    this.hashCode());
        System.out.println("serviceImpl  第" + integer.incrementAndGet() + " 次调用");
        System.out.println("服务端收到消息： " + message);
        if(message != null){
            return  "客户端已经收到你的消息： " + message;
        }
        return "收到消息";
    }
}
