package com.embrace.practice.netty.dubborpc.netty;

import com.embrace.practice.netty.dubborpc.provider.HelloServiceImpl;
import com.embrace.practice.netty.dubborpc.service.HelloService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/10 23:19
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("客户端发来消息： " + msg);
        if(msg.toString().startsWith("service#hello#")){
            //客户端带来的数据会有某种协议，这里 就以service#hello# 代替
            String  msgString = msg.toString();
            String result = new HelloServiceImpl().hello(msgString.substring(msgString.lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       ctx.close();
    }
}
