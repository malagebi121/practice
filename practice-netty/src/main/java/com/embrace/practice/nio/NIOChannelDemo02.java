package com.embrace.practice.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author embrace
 * @describe   将文件的数据打印出来
 * @date created in 2021/1/1 18:35
 */
public class NIOChannelDemo02 {
    public static void main(String[] args) throws Exception{
        // 输入流
        FileInputStream fileInputStream = new FileInputStream("F:\\01.txt");
        // 获取通道
        FileChannel fileChannel = fileInputStream.getChannel();
        // 新建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 将数据读取到 buffer 中
        fileChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
    }
}
