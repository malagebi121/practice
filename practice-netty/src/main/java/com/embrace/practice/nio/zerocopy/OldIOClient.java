package com.embrace.practice.nio.zerocopy;


import java.io.*;
import java.net.Socket;

/**
 * @author embrace
 * @describe 传统IO客户端
 * @date created in 2021/1/5 11:42
 */
public class OldIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 7001);
        FileInputStream inputStream = new FileInputStream("F:\\a.txt");
        //out流
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[2048];
        long readCount;
        long total = 0L;
        long startTime = System.currentTimeMillis();
        while ( (readCount = inputStream.read(buffer)) > 0){
            total += readCount;
            dataOutputStream.write(buffer);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("花费 " + (endTime - startTime) + " 共发送 " + total + "字节");
        //关闭流
        socket.close();
        dataOutputStream.close();
        inputStream.close();
    }
}
