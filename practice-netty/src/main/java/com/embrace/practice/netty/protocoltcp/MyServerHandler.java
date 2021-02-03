package com.embrace.practice.netty.protocoltcp;

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
public class MyServerHandler  extends SimpleChannelInboundHandler<MessageProtocol> {

    private AtomicInteger integer = new AtomicInteger(0);
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
//        byte[] b = new  byte[byteBuf.readableBytes()];
//        byteBuf.readBytes(b);
//        System.out.println("服务器端就收到的数据 ： " + new String(b, CharsetUtil.UTF_8));
//        System.out.println("接收到的次数 ： " + integer.incrementAndGet());
//
//        ByteBuf responseMsg = Unpooled.copiedBuffer(UUID.randomUUID().toString(), CharsetUtil.UTF_8);
//        channelHandlerContext.writeAndFlush(responseMsg);
//    }

    // 发送几次就会调用几次，
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
        int length = messageProtocol.getLength();
        byte[] content = messageProtocol.getContent();

        System.out.println("客户端发送的长度：" + length);
        System.out.println("客户端发送的消息: "  +  new String(content, CharsetUtil.UTF_8) );

        System.out.println("服务器就收到的系数： "  + integer.incrementAndGet());

        // 回复消息
        String response = "我是返回呀";

        byte[] bytes = response.getBytes("utf-8");
        int len = bytes.length;

        MessageProtocol messageProtocol1 = new MessageProtocol();
        messageProtocol1.setContent(bytes);
        messageProtocol1.setLength(len);

        channelHandlerContext.writeAndFlush(messageProtocol1);


    }
}
