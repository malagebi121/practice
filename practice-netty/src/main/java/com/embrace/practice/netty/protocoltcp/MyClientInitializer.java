package com.embrace.practice.netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/10 17:53
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //加入编码器
        pipeline.addLast(new MyMessageEncoder());
        //解码器
        pipeline.addLast(new MyMessageDecoder());
        //加入自定义的handler
        pipeline.addLast(new MyClientHandler());
    }
}
