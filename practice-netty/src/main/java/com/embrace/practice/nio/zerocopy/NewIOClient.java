package com.embrace.practice.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/5 13:47
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        //打开服务端的socket
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",6667));
//        socketChannel.connect(new InetSocketAddress("127.0.0.1",6667));
        socketChannel.configureBlocking(false);
        //文件
        FileInputStream inputStream = new FileInputStream("F:\\a.txt");
        FileChannel fileChannel = inputStream.getChannel();
        long startTime = System.currentTimeMillis();
        //在linux下可以全部传输
        //但是在windows下面不得行，最大8M，需要分段传输
        long  transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        long endTime = System.currentTimeMillis();
        System.out.println("总共花费时间 " + (endTime - startTime) + "共传输" + transferCount);
        socketChannel.close();
        inputStream.close();
        fileChannel.close();
    }
}
