package com.embrace.practice.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/7 17:38
 */
public class NettyByteBuf {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer(10);
        //netty 的 byteBuffer
        //不用像netty 哪像每次读写反转需要 flip
        //ByteBuf 底层维护了readerIndex  和 writerIndex
        for (int i = 0; i < 10; i++) {
            byteBuf.writeByte(i);
        }

        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.getByte(i));
            byteBuf.readByte(); //这个会导致readerIndex
        }

    }
}
