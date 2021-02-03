package com.embrace.practice.netty.inandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/9 22:32
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //加入一个出站的handler编码器
        pipeline.addLast("encoder",new MyLongToByteEncoder());

        //加入自定义的handler
        pipeline.addLast(new  MyClientHandler());
    }
}
