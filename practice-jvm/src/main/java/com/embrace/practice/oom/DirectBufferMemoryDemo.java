package com.embrace.practice.oom;

import java.nio.ByteBuffer;

/**
 * @author embrace
 * @describe  异常情况 Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
 *            -Xms10m -Xmx10m  -XX:+PrintGCDetails -XX:MaxDirectMemorySize=10m
 *            (-XX:MetaspaceSize=10m )  规定元空间也是可以的   java -XX:+PrintFlagsFinal查看初始化信息
 *
 * 元空间和永久代都是对jvm规范方法区的实现，最大的区别在于，
 * 永久代在堆空间，元空间不再虚拟机中，而是在本地内存（重要），所以说，
 * 一般默认情况下，元空间只受限于本地内存
 *
 *
 * 导致原因
 *    在NIO用byteBuffer来读取写入数据时候，这是一种基于通道（Channel）和缓冲区（Buffer） 的通信方式,
 *    它可以使用native函数库直接使用堆外内存，然后通过在java堆里面的 DirectByteBuffer 对象对这块内存
 *    的引用进行操作，这样能在一些读写场景中显著地提高性能，因为避免了在java堆和native堆中的来回复制数据
 *
 *    ByteBuffer.allocate(int capacity); 调用的的Jvm的堆内存，受到GC的管辖，由于需要拷贝，出于对效率速度的考虑
 *    ByteBuffer.allocateDirect(int capacity); 调用本地内存，不属于GC管辖，不需要拷贝所以快
 *    但是本地内存使用超出了限制，GC 又不会回收，直接就报异常了
 * @date created in 2020/12/22 23:15
 */
public class DirectBufferMemoryDemo {
    public static void main(String[] args) {
        System.out.println("配置的直接堆外内存：" + sun.misc.VM.maxDirectMemory()/(1024*1204) + "Mb" ); //3069Mb  我的怎么不到四分之一
        ByteBuffer bt = ByteBuffer.allocateDirect(20 * 1024 * 1024);
    }
}
