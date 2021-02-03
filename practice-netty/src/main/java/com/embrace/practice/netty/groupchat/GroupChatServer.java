package com.embrace.practice.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author embrace
 * @describe   群聊系统服务端
 * @date created in 2021/1/7 20:30
 */
public class GroupChatServer {

    private int  port;

    public GroupChatServer(int port) {
        this.port = port;
    }

    //编写一个run方法
    public void run(){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //链式编程
            bootstrap.group(workGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)//设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//保持活动状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            //向pipeline 加入解码器
                            pipeline.addLast("decoder",new StringDecoder());
                            //加入编码器
                            pipeline.addLast("coder",new StringEncoder());
                            //加入自己的handler 处理
                            pipeline.addLast(new GroupChatServerHandler());
                        }
                    }); // 自定义的handler
            System.out.println("netty 服务器启动");
            //绑定端口并且异步， 生成 channelFuture 对象
            ChannelFuture channelFuture = bootstrap.bind(port).sync();

            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        System.out.println("监听端口 " + port + " 成功");
                    }else{
                        System.out.println("监听端口 " + port + " 失败");
                    }
                }
            });

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅关闭
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        //启动服务
        new GroupChatServer(7000).run();
    }
}
