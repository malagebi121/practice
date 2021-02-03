package com.embrace.practice.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author embrace
 * @describe    buffer 的分散和聚合
 * @date created in 2021/1/1 21:52
 */
public class BufferScatteringAndGatheringDemo {
    public static void main(String[] args) throws  Exception{
        // 使用ServerSocketChannel  和 SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(2);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待client 端的 socket
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8;
        while (true){
            int byteRead = 0;
            while (byteRead< messageLength){
                long l = socketChannel.read(byteBuffers);
                byteRead += l;
                System.out.println("byteRead = " + byteRead);
                Arrays.asList(byteBuffers)
                        .stream()
                        .map(buffer -> "position : " + buffer.position()  + "  limit:" + buffer.limit())
                        .forEach(System.out::println);
                //flip()
                Arrays.asList(byteBuffers).stream().forEach(byteBuffer -> byteBuffer.flip());

                //打印
                long byteWrite= 0L;
                while (byteWrite < messageLength){
                    long a =socketChannel.write(byteBuffers);
                    byteWrite += a;
                }
                //flip()
                Arrays.asList(byteBuffers).stream().forEach(byteBuffer -> byteBuffer.clear());

                System.out.println("byteRead :  " + byteRead +" byteRead:" + byteRead+ " " + messageLength);
            }
        }
    }
}
