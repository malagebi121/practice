package com.embrace.practice.netty.inandoutboundhandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.channels.SocketChannel;
import java.util.logging.SocketHandler;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/9 22:35
 */
public class MyClientHandler extends SimpleChannelInboundHandler<SocketChannel> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SocketChannel socketChannel) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler 向服务端发送数据");
//        ctx.writeAndFlush(Unpooled.copiedBuffer(""));
        ctx.writeAndFlush(123456L);
    }
}
