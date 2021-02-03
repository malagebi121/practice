package com.embrace.practice.netty.inandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/9 22:37
 */
public class MyLongToByteEncoder  extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder  encoder 被调用了");
        System.out.println("数据："  + msg);
        out.writeLong(msg);
    }
}
