package com.embrace.practice.netty.websocket;

import com.embrace.practice.netty.heartbeat.HeartBeatServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/8 9:32
 */
public class WebSocketServer {
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
                    //基于http处理，需要添加httpCodec 编码解码器
                    pipeline.addLast(new HttpServerCodec());
                    //一块的方式处理，添加ChunkedWriteHandler处理器
                    pipeline.addLast(new ChunkedWriteHandler());
                    //在http传输中数据是分段的，可以将多个段聚合起来
                    //这就是为什么流浪器发送大量数据时，会发送大量的http请求
                    pipeline.addLast(new HttpObjectAggregator(8192));
                    //http 传输数据是以帧（frame） 的方式传输的
                    // WebSocketFrame,下面有6 个子类
                    // 如果以 http://localhost:7000/hello  传输对应的地址要写 hello
                    // WebSocketServerProtocolHandler 能将 http 的连升级为 ws 协议保持长连接
                    pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                    pipeline.addLast(new WebSocketServerHandler());
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
