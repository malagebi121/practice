package com.embrace.practice.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/10 18:41
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder 被调用了");
        //二进制 转化为 数据
        int length = in.readInt();
        byte[] b = new byte[length];
        in.readBytes(b);

        //传递给下一个handler
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLength(length);
        messageProtocol.setContent(b);

        out.add(messageProtocol);

    }
}
