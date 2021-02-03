package com.embrace.practice.nio;

import java.nio.ByteBuffer;

/**
 * @author embrace
 * @describe   java.nio.ReadOnlyBufferException
 * @date created in 2021/1/1 21:12
 */
public class NIOBufferReadOnly {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        for (int i = 0; i < 32; i++) {
            buffer.put((byte)i);
        }

        buffer.flip();
        //只读
        ByteBuffer byteBuffer = buffer.asReadOnlyBuffer();
        //循环
        while(byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }
        //如果添加直接报错
        byteBuffer.put((byte) 1);


    }
}
