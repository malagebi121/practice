package com.embrace.practice.netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * @author embrace
 * @describe
 * @date created in 2021/1/10 17:50
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new MyMessageDecoder()); //解码器
        pipeline.addLast(new MyMessageEncoder()); //编码器
        pipeline.addLast(new MyServerHandler()); // 自定义handler
    }
}
