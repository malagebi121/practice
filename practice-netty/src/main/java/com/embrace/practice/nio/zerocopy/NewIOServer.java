package com.embrace.practice.nio.zerocopy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author embrace
 * @describe NIO 的网络传输
 * @date created in 2021/1/5 13:47
 */
public class NewIOServer {
    public static void main(String[] args) throws IOException {
        //打卡服务端的Server
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(6667));
        serverSocketChannel.configureBlocking(false);

        //输出流写文件
        FileOutputStream fileOutputStream = new FileOutputStream("F:\\b.txt");

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel != null){
                int read = 0;
                int allCount = 0;
                while (-1 != read){
                    try {
                        read = socketChannel.read(byteBuffer);
                        allCount += read;
                    } catch (IOException e) {
                        System.out.println("共接收：" + allCount);
                        break;
                    }
                    // buffer 倒带，这里优点问题
//                    byteBuffer.rewind();
                    //将数据写入到文件
                    fileOutputStream.write(byteBuffer.array());
                }
                //关闭流
            }
        }
    }
}
