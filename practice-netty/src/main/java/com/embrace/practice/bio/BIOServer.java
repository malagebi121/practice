package com.embrace.practice.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @author embrace
 * @describe  BIO server
 *      利用 telnet 客户端进行请求连接
 *      telnet 127.0.0.1 6666 请求服务器
 *      ctrl + ] 进入
 *      send 数据
 * @date created in 2021/1/1 15:19
 */
public class BIOServer {
    public static void main(String[] args) throws Exception {
        //1.创建一个线程池
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        // 新建一个 serverSocket 服务端
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务端启动了");
        //2.一旦有链接就启动一个线程
        while (true){
            System.out.println("主线程等待连接：" + Thread.currentThread().getName());
            //客户端没有数会阻塞在这里
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            newCachedThreadPool.submit( () -> {
                solveSocket(socket);
            });
        }
    }

    // 处理 socket 的方法
    private static void solveSocket(Socket socket) {
        //快捷键 Ctrl + Alt + T
        try {
            System.out.println("当前线程： " + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            //通过socket 获取输入流
            InputStream inputStream = socket.getInputStream();
            while (true){
                //连接没有数据了会阻塞在这里
                System.out.println("read 等待连接： " + Thread.currentThread().getName());
                int  read = inputStream.read(bytes);
                if(read != -1){
                    System.out.println(new String(bytes, 0, read));
                }else{
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println("连接关闭");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
