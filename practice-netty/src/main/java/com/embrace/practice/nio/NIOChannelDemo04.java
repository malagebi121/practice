package com.embrace.practice.nio;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * @author embrace
 * @describe  用 Channel 的  transferFrom 的方法拷贝
 * @date created in 2021/1/1 20:40
 */
public class NIOChannelDemo04 {
    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        FileOutputStream outputStream = null;
        FileChannel channel1 = null;
        FileChannel channel2 = null;
        try {
            fileInputStream = new FileInputStream("F:\\1.jpeg");
            outputStream = new FileOutputStream("F:\\2.jpeg");

            channel1 = fileInputStream.getChannel();
            channel2 = outputStream.getChannel();

            channel2.transferFrom(channel1,0, channel1.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(channel1 != null){
                try {
                    channel1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }if(channel2 != null){
                try {
                    channel2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
