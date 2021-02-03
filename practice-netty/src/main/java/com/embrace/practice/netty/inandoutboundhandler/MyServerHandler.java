package com.embrace.practice.netty.inandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/9 22:23
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long l) throws Exception {
        System.out.println("从客户端"+ channelHandlerContext.channel().remoteAddress()+"读取到:" + l);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
