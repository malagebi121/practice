package com.embrace.practice.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
/**
 * @author embrace
 * @describe  群聊客户端
 * @date created in 2021/1/4 21:01
 */
public class GroupChatClient {
    private final static String HOST = "127.0.0.1";
    private final static Integer PORT = 6700;
    private SocketChannel socketChannel;
    private Selector selector;
    private String  userName;

    public GroupChatClient() throws IOException {
        socketChannel = socketChannel.open(new InetSocketAddress(HOST, PORT));
        selector = Selector.open();
        socketChannel.configureBlocking(false);
        //忘记注册了，日了鬼了，搞了半个小时菜发现
        socketChannel.register(selector, SelectionKey.OP_READ);
        userName = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println("客户端：" + userName + " 启动好了");
    }

    public void sendMsg(String msg){
        try {
            String  info = userName + " : " +msg;
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMsg(){
        try {
            int count = selector.select();
            if(count > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    if(selectionKey.isReadable()){
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        channel.read(byteBuffer);
                        String serverMsg = new String(byteBuffer.array());
                        System.out.println(serverMsg.trim() + "");
                    }
                    iterator.remove();
                }
            }else{
                System.out.println("没有可用的通道");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatClient client = new GroupChatClient();
        //客户端循环读取数据
        new Thread(() -> {
            while(true){
                System.out.println("读取消息启动");
                client.readMsg();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //获得控制台数据，发送消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            client.sendMsg(s);
        }

    }
}
