package com.embrace.practice.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author embrace
 * @describe   用自定义的协议包处理信息
 * @date created in 2021/1/10 17:54
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private AtomicInteger integer = new AtomicInteger(0);

//    //读取客户端返回数据
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
//        byte[] b = new  byte[byteBuf.readableBytes()];
//        byteBuf.readBytes(b);
//        System.out.println("服务器端就收到的数据 ： " + new String(b, CharsetUtil.UTF_8));
//        System.out.println("接收到的次数 ： " + integer.incrementAndGet());
//    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol msg) throws Exception {
        int len = msg.getLength();
        byte[] content = msg.getContent();
        System.out.println("接收到的长度" + len);
        System.out.println("接收到的数据：" + new String(content,CharsetUtil.UTF_8));
        System.out.println("调用的次数： " + integer.incrementAndGet());
    }

    // 发送数据
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 5; i++) {
            String msg = "今天天气冷，吃火锅 ：" + i;
            byte[] content =msg.getBytes(CharsetUtil.UTF_8);
            int length = content.length;

            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setContent(content);
            messageProtocol.setLength(length);

            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常信息： " + cause.getMessage());
        ctx.close();
    }

}
