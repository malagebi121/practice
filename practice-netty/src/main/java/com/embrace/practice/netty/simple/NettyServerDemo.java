package com.embrace.practice.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/5 22:18
 */
public class NettyServerDemo {
    static List<SocketChannel> list = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        // 第一步
        //创建两个线程组 bossGroup 和 workGroup
        //bossGroup 进行连接请求 workGroup 处理业务请求
        //两者都是无限循环
        //子线程数是cpu 核心数的 2 倍 ，NettyRuntime.availableProcessors() * 2
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1); //带参数设置子线程数
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            //创建服务器端的启动对象，启动参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用链式编程来设置
            bootstrap.group(bossGroup, workGroup)//设置线程组
                    .channel(NioServerSocketChannel.class)//设置channel来服务器的通道
                    .option(ChannelOption.SO_BACKLOG,128)//设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//保持活动状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//匿名类部内通道
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //这里的socketChannel 可用一个集合装载，获得EventLoop ,
                            System.out.println("客户的 socketChannel ： " + socketChannel.hashCode());
                            list.add(socketChannel);

                            //给管道设置处理器, 自定义的处理器
                            socketChannel.pipeline().addLast(new NettyServerHandler());;
                        }
                    });
            System.out.println("服务器准备好了");
            //绑定端口并且同步， 生成 channelFuture 对象
            ChannelFuture channelFuture = bootstrap.bind(6688).sync();

            //给channelFuture做相应的监听器
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        System.out.println("监听6688成功" );
                    }else{
                        System.out.println("监听6688失败" );
                    }
                }
            });

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e){
            //优雅关闭流
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}


