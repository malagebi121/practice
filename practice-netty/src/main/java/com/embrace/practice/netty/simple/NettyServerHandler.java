package com.embrace.practice.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author embrace
 * @describe   自定义的 handler 需要继承 netty 定义好的 HandlerAdapter  ，handler 入栈的适配器
 * @date created in 2021/1/5 23:22
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端消息
     * @param ctx 上下文对象，含有管道（pipeline） ，通道（channel） ，地址
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //如果这里有很长时间的操作怎么办
        //睡眠10秒，模拟处理任务，这里会阻塞
//        Thread.sleep(10 * 1000);
        ctx.writeAndFlush(Unpooled.copiedBuffer("我是服务端，喵喵1",CharsetUtil.UTF_8));

        //任务队列3种常用的方式

        //用任务异步队列
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("我是服务端，任务处理中,任务队列1",CharsetUtil.UTF_8));
        });

        //用任务异步队列
        ctx.channel().eventLoop().execute(() -> { //加入了一个队列， 需要10 + 20 秒才执行
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("我是服务端，任务处理中,任务队列2",CharsetUtil.UTF_8));
        });

        //异步延时队列， 和上面的不在一个队列中，但是还是要等待上面的执行了才执行
        ctx.channel().eventLoop().schedule(() ->{
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("我是服务端，任务处理中,任务队列3",CharsetUtil.UTF_8));
        },5L, TimeUnit.SECONDS);


//        System.out.println("当前线程： " + Thread.currentThread().getName());
//
//        System.out.println("server ctx ：" + ctx);
//        Channel channel = ctx.channel();
//        ChannelPipeline pipeline = ctx.pipeline(); //本质上时一个双向链表，出站入站的问题
//
//
//        //将msg转化为ByteBuf
//        //ByteBuf 是 Netty 提供的，不是 NIO 里面的 ByteBuffer
//        ByteBuf byteBuf = (ByteBuf) msg;
//        System.out.println("客户端发送的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址是：" + ctx.channel().remoteAddress());

    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("我是服务端，努力吧骚年，喵喵2",CharsetUtil.UTF_8));
    }
    //异常关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
