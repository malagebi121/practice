package com.embrace.practice.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/7 10:25
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //netty 的编解码器 code -> codec 编码解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //自己的handler
        pipeline.addLast("MyHandler",new HttpServerHandler());
    }
}
