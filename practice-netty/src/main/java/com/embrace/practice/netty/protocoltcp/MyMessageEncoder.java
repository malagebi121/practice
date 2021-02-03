package com.embrace.practice.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/10 18:38
 */
public class MyMessageEncoder extends MessageToByteEncoder<MessageProtocol> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol, ByteBuf byteBuf) throws Exception {
        System.out.println("MyMessageEncoder 被调用");
        byteBuf.writeInt(messageProtocol.getLength());
        byteBuf.writeBytes(messageProtocol.getContent());
    }
}
