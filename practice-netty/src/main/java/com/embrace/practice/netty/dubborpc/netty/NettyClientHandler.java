package com.embrace.practice.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/10 23:34
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context; // 上下文
    private String result; //返回的结果
    private String para; //传入的参数

    // 与服务器连接就会调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       context = ctx;
    }

    // 读取的时候
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify(); // 唤醒 call 等待的线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    // 被代理对象调用，发送给服务器， wait() 等待被唤醒
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(para);
        wait(); // 等待
        return result; //  等待服务器端获取结果唤醒
    }

    void serPara(String  para){
        this.para = para;
    }
}
