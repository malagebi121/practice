package com.embrace.practice.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author embrace
 * @describe  群聊系统server端
 * @date created in 2021/1/4 16:52
 */
public class GroupChatServer {
    //Selector 选择器
    private Selector selector;
    //服务端的 ServerSocketChannel
    private ServerSocketChannel serverSocketChannel;
    //服务端端口
    private static final int PORT = 6700;

    public GroupChatServer(){
        try {
            selector = selector.open();
            serverSocketChannel = serverSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("初始化server成功，端口：" + PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //监听
    public void listen(){
        try {
            while(true){
                int count = selector.select(1000);
                if( count > 0){
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey selectionKey = iterator.next();
                        if(selectionKey.isAcceptable()){
                            //接入事件
                            accept();
                        }
                        if(selectionKey.isReadable()){
                            //读取时间
                            read(selectionKey);
                        }
                        //完事之后移除key
                        iterator.remove();
                    }
                }else{
//                    System.out.println("无事件发生");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读事件
    private void read(SelectionKey selectionKey) {
        SocketChannel socketChannel = null;
        try {
            //得到关联的socketChannel
             socketChannel = (SocketChannel)selectionKey.channel();
            //得到byteBuffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);  // 也可以直接 ByteBuffer.allocate(1024);
            //数据读进buffer
            int count = socketChannel.read(byteBuffer);
            if(count > 0){
                String  msg = new String(byteBuffer.array());
                System.out.println("发来信息：" + msg);
                //转发消息给其他通道，需要消息还有通道地址，排除自己
                sendIntoToOthers(msg, socketChannel);
            }
        } catch (IOException e) {
            //哪里处理客户下线问题
            try {
                System.out.println("客户端：" + socketChannel.getRemoteAddress().toString().substring(1) + " 离线了" );
                socketChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void sendIntoToOthers(String msg, SocketChannel target) throws IOException {
        System.out.println("转发消息中");
        for(SelectionKey selectionKey : selector.keys()){
            Channel channel = selectionKey.channel();
            //给其他全部的发送消息
            //判断channel 是不是 SocketChannel 且不等 自己
            if(channel instanceof SocketChannel && channel != target){
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                SocketChannel socketChannel = (SocketChannel) channel;
                socketChannel.write(byteBuffer);
                System.out.println("向" + socketChannel.getRemoteAddress() + " 发送消息成功" );
            }
        }
    }

    //接入事件
    public void accept(){
        try {
            //得到客户端的socketChannel
            SocketChannel socketChannel = serverSocketChannel.accept();
            //设置非阻塞
            socketChannel.configureBlocking(false);
            //将socketChannel注入到selector
            socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
            System.out.println("客户端: " + socketChannel.getRemoteAddress() + " 上线了");
            //上线消息，发给其他人
            sendIntoToOthers(socketChannel.getRemoteAddress() + "上线了", socketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();

    }
}
