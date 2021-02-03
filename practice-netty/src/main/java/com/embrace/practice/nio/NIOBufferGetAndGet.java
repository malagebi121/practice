package com.embrace.practice.nio;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.nio.ByteBuffer;

/**
 * @author embrace
 * @describe   按类型放，按类型取出
 *     不然可能报错  java.nio.BufferUnderflowException
 * @date created in 2021/1/1 20:58
 */
public class NIOBufferGetAndGet {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);

        buffer.putInt(100);
        buffer.putLong(9L);
        buffer.putChar('张');
        buffer.putShort((short)4);

        System.out.println("position = " + buffer.position());
        buffer.flip();
        //按顺序没问题
//        System.out.println(buffer.getInt());
//        System.out.println(buffer.getLong());
//        System.out.println(buffer.getChar());
//        System.out.println(buffer.getShort());

        //java.nio.BufferUnderflowException
        System.out.println(buffer.getInt());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getLong());

    }
}
