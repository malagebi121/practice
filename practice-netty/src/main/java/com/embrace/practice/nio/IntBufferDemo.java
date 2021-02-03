package com.embrace.practice.nio;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
/**
 * @author embrace
 * @describe
 *     buffer 属性
 *     private int mark = -1; 调用mark() 来设置mark = position
 *     private int position = 0; 下一个要被读或者写的位置
 *     private int limit;  缓冲区当前的终点，不能对超过该终点的缓冲区进行读写操作
 *     private int capacity; 容量
 * @date created in 2021/1/1 16:57
 */
public class IntBufferDemo {
    public static void main(String[] args) {
        //创建一个个数为 10 容量的buffer
        IntBuffer intBuffer = IntBuffer.allocate(10);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(5);//使用直接内存
        //向buffer中存放数据,超过容量会报错
        for(int  i = 0 ; i < 10; i ++){
            intBuffer.put(i);
        }
        // 写读转换
        intBuffer.flip();
        //如果还有，继续

        intBuffer.position(0); // 可以设置这些来改变读取位置
        intBuffer.limit(3);
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
