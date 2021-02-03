package com.embrace.practice.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author embrace
 * @describe  NIO 传输服务端
 * 步骤：
 * 开启ServerSocketChannel，设置非阻塞，绑定端口
 * 开启ServerSocketChannel 注册 Selector
 * 循环监听Selector，如果有事件就处理没，没有就算了
 * 循环遍历selectionKeys.iterator()的selectionKey
 * 根据的selectionKey的接入事件还有反向获取SocketChannel 进行相应的处理
 * @date created in 2021/1/4 13:08
 */
public class NIOServerDemo {
    public static void main(String[] args) throws Exception{
        //服务端的serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //端口地址
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6667);
        serverSocketChannel.bind(inetSocketAddress);
        //seletor
        Selector selector = Selector.open();
        //将 Selector 注册到 ServerSocketChannel上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //selectedKeys（发生事件的个数）  keys(所有的key)
        System.out.println("server初始化，selector的size : " + selector.keys().size());
        //循环等待客户端连接
        while (true){
            if(selector.select(1000) == 0){ //没有事件发生
                System.out.println("等待1秒，没有客户端事件，继续 ...");
                continue;
            }
            //获取有事件的 key 集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //反向获取通道
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                //接入事件
                if(selectionKey.isAcceptable()){
                    //给该客户端生成socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //将socketChannel设置非阻塞
                    socketChannel.configureBlocking(false);
                    //注册到selector,事件,并给一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("客户端连接成功, hash ："+ socketChannel.hashCode());
                    //selectedKeys  的话第一个进来是1 ，keys 为2
                    System.out.println("客户端连接后,注册到selector, selector的size : " + selector.keys().size());

                }
                //可读事件
                if(selectionKey.isReadable()){
                    //通过key 反向获取
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(byteBuffer);
                    System.out.println("From 客户端数据:" + new String(byteBuffer.array()));
                }
                //移除
                iterator.remove();
            }
        }
    }
}
