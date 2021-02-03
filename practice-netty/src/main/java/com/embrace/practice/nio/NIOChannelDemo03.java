package com.embrace.practice.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author embrace
 * @describe  用一个buffer拷贝文件
 *      一行后面的换行也会占两个byte
 * @date created in 2021/1/1 18:42
 */
public class NIOChannelDemo03 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("F:\\1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("F:\\2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(5);
        while (true){
            int read = fileChannel01.read(buffer);
            System.out.println("read = " + read);
            if(read == -1){
                break;
            }
            buffer.flip();
            fileChannel02.write(buffer);
            buffer.clear(); //这个屏蔽了直接不能停止运行也没有生成文件
        }
        //关闭流
        fileChannel01.close();
        fileChannel02.close();
    }
}
