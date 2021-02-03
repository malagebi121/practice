package com.embrace.practice.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author embrace
 * @describe  将数据写入文件
 * @date created in 2021/1/1 18:21
 */
public class NIOChannelDemo01 {
    public static void main(String[] args) throws Exception{
        String s = "aaa非一日之寒"; //一个英文就是一个字节，一个汉字对应三个字节
        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("F:\\01.txt");
        // 获取输出流的channel
        // 这个channel的知识类型是 FileChannelImpl.open(fd, path, false, true, append, this); 实现类
        FileChannel fileChannel = fileOutputStream.getChannel();
        //新建缓冲区buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024); //position : 18
        // 将数据放入buffer
        buffer.put(s.getBytes());
        //重置position
        buffer.flip();
        //写入
        fileChannel.write(buffer);
        //关闭流
        fileChannel.close();
    }
}
