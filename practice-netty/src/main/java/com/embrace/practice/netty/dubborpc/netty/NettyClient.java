package com.embrace.practice.netty.dubborpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/10 23:45
 */
public class NettyClient {
    //创建线程池
    private  static ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    private static NettyClientHandler client;

    private AtomicInteger integer = new AtomicInteger(0);

    //编写代理模式使用代理对象
    public Object getBean(final Class<?> serviceClass, String providerName ){
        //newProxyInstance(ClassLoader loader,Class<?>[] interfaces,  InvocationHandler h)
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass},(proxy, method, args) ->{
                    System.out.println("被调用：" + integer.incrementAndGet() + "次");
                    // 每调用都要执行一次
                    if(client == null){
                        initClient();
                    }
                    client.serPara(providerName + args[0]);
                    return executorService.submit(client).get();
                });
    }

    static void initClient(){
        client = new NettyClientHandler();
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(client);
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6700).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
