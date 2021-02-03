package com.embrace.practice.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/10 17:52
 */
public class MyServerHandler  extends SimpleChannelInboundHandler<ByteBuf> {

    private AtomicInteger integer = new AtomicInteger(0);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte[] b = new  byte[byteBuf.readableBytes()];
        byteBuf.readBytes(b);
        System.out.println("服务器端就收到的数据 ： " + new String(b, CharsetUtil.UTF_8));
        System.out.println("接收到的次数 ： " + integer.incrementAndGet());

        ByteBuf responseMsg = Unpooled.copiedBuffer(UUID.randomUUID().toString(), CharsetUtil.UTF_8);
        channelHandlerContext.writeAndFlush(responseMsg);
    }

}
