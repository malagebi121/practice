package com.embrace.practice.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author embrace
 * @describe
 * @date created in 2021/1/10 17:54
 */
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private AtomicInteger integer = new AtomicInteger(0);

    //读取客户端返回数据
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte[] b = new  byte[byteBuf.readableBytes()];
        byteBuf.readBytes(b);
        System.out.println("服务器端就收到的数据 ： " + new String(b, CharsetUtil.UTF_8));
        System.out.println("接收到的次数 ： " + integer.incrementAndGet());
    }

    // 发送数据
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ByteBuf by = Unpooled.copiedBuffer("hello,server" + i , CharsetUtil.UTF_8);
            ctx.writeAndFlush(by);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
