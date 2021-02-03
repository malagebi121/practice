package com.embrace.practice.netty.inandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


/**
 * @author embrace
 * @describe
 * @date created in 2021/1/9 22:15
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     *
     * @param channelHandlerContext  上下文
     * @param byteBuf 入站的 byteBuf 数据
     * @param list  将入站的数据集合传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //long 有8个字节，所以有了8个字节才是一个long
        if(byteBuf.readableBytes() >= 8){
            list.add(byteBuf.readLong());
        }
    }
}
