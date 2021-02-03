package com.embrace.practice.netty.protocoltcp;

/**
 * @author embrace
 * @describe
 *
 * 协议包
 *
 * @date created in 2021/1/10 18:26
 */
public class MessageProtocol {
    private int length;

    private byte[] content;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
