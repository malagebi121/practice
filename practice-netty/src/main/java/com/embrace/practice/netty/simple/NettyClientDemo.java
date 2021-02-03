package com.embrace.practice.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author embrace
 * @describe  Netty客户端
 * @date created in 2021/1/6 18:02
 */
public class NettyClientDemo {
    public static void main(String[] args) {
        //客户端需要一个时间循环组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //客户端用bootstrap
            Bootstrap bootstrap = new Bootstrap();
            //链式编程
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new NettyClientHandler());
                        }
                    });

            System.out.println("客户端OK");
            //启动客户端去连接服务
            //ChannelFuture 设计netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6688).sync();

            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        System.out.println("监听6688成功");
                    }else{
                        System.out.println("监听6688失败");
                    }
                }
            });

            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //优雅关闭
            group.shutdownGracefully();
        }

    }
}
