package com.embrace.practice.nio.zerocopy;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author embrace
 * @describe 传统IO的服务器端
 * @date created in 2021/1/5 11:41
 */
public class OldIOServer {
    public static void main(String[] args)  throws IOException {
        ServerSocket serverSocket = new ServerSocket(7001);
        while (true){
            //等待客户端连接
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream("F:\\b.txt");
            byte[] buffer = new byte[2048];
            while (true){
                int readCount = dataInputStream.read(buffer,0, buffer.length);
                if(readCount == -1){
                    break;
                }
                fileOutputStream.write(buffer);
            }
        }
    }
}
