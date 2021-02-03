package com.embrace.practice.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author embrace
 * @describe 心跳服务端
 * @date created in 2021/1/7 22:50
 */
public class HeartBeatServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            //链式写法
            bootstrap.group(workGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG));
            //也可以这样写
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    //IdleStateHandler  netty提供的状态处理器
                    // IdleStateHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit)
                    //readerIdleTime  表示多长时间没有读，没有读就发送一个心跳包去测试是否连接
                    //writerIdleTime  表示多长时间没有写，没有读就发送一个心跳包去测试是否连接
                    //allIdleTime  多长时间没有读写，没有读就发送一个心跳包去测试是否连接
                    //当IdleStateHandler 触发后，会传递给下一个handler ，并触发 userEventTriggered
                    pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));

                    //加一个自己的空闲检测
                    pipeline.addLast(new HeartBeatServerHandler());
                }
            });

            ChannelFuture cf = bootstrap.bind(7000).sync();
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }


    }
}
