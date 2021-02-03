package com.embrace.practice.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author embrace
 * @describe  客户端的Handler
 * @date created in 2021/1/6 18:21
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //当通道就绪后触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端 ：" + ctx );
        ctx.writeAndFlush(Unpooled.copiedBuffer("我是客户端，骚年努力吧", CharsetUtil.UTF_8));
    }

    //当通道有读取事件触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务端发送的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务端地址是：" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
