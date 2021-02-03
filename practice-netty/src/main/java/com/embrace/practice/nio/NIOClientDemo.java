package com.embrace.practice.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author embrace
 * @describe NIO 传输客户端
 * 步骤：
 * 创建SocketChannel 设置非阻塞
 * 绑定端口非阻塞设置连接信息
 * socketChannel.write(buffer)  写入buffer发送信息
 * @date created in 2021/1/4 13:47
 */
public class NIOClientDemo {
    public static void main(String[] args) throws Exception {
        //打开端口
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //网络端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6667);
        //非阻塞等待连接
        if(!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("连接中... 客户端可以做其他事");
            }
        }
        String s = "冲起来吧，骚年";
        ByteBuffer buffer = ByteBuffer.wrap(s.getBytes());
        socketChannel.write(buffer);
        System.in.read();
    }
}
