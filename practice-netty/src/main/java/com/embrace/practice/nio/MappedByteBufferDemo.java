package com.embrace.practice.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author embrace
 * @describe   直接在内存中直接修改
 * @date created in 2021/1/1 21:32
 */
public class MappedByteBufferDemo {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("F:\\01.txt","rw");
        FileChannel channel = randomAccessFile.getChannel();
        // 使用读写模式， 从 position 0 位置，到位置 5 的字节
        //实际上是  DirectByteBuffer
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(1,(byte)'H');
        mappedByteBuffer.put(2,(byte)9);

        randomAccessFile.close();
    }
}
