package com.embrace.practice.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/7 20:07
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {
        // 数据转buffer
        ByteBuf byteBuf = Unpooled.copiedBuffer("你好啊，小理", CharsetUtil.UTF_8);

        byte[] bytes = byteBuf.array();

        System.out.println(new String(bytes,CharsetUtil.UTF_8));

        System.out.println("byteBuf : " + byteBuf);

        System.out.println(byteBuf.arrayOffset()); //0
        System.out.println(byteBuf.readerIndex()); //0
        System.out.println(byteBuf.writerIndex()); //18
        System.out.println(byteBuf.readableBytes()); //18
        System.out.println(byteBuf.capacity()); //18

        //范围读取
        System.out.println(byteBuf.getCharSequence(0,2,CharsetUtil.UTF_8)); //从零开始读取3个就是一个汉字
    }
}
