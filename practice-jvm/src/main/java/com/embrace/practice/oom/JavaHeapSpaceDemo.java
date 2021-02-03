package com.embrace.practice.oom;

/**
 * @author embrace
 * @describe    故障现象   java.lang.OutOfMemoryError: Java heap space
 *              -Xms5m -Xmx5m -XX:+PrintGCDetails
 *             堆溢出,对象太大，多次垃圾回收了之后还是放不下，直接抛出异常
 *
 * @date created in 2020/12/22 17:29
 */
public class JavaHeapSpaceDemo {
    public static void main(String[] args) {
        // 5m 的最大堆内存直接崩塌, 8Mb 直接干翻
        byte[] b = new byte[8 * 1024 * 1024]; //java.lang.OutOfMemoryError: Java heap space
    }
}
