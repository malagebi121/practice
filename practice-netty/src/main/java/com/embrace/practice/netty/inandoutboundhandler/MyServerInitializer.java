package com.embrace.practice.netty.inandoutboundhandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author embrace
 * @describe   childHandler 里面的 ChannelInitializer
 * @date created in 2021/1/9 22:12
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // 入站的解码器
        pipeline.addLast("decoder",new MyByteToLongDecoder());

        pipeline.addLast(new MyServerHandler());
    }
}
