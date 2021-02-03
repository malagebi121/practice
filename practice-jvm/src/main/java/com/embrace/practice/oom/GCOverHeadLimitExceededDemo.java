package com.embrace.practice.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author embrace
 * @describe
 *              故障现象  java.lang.OutOfMemoryError: GC overhead limit exceeded
 *              -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 *              垃圾回收太少，一次内存回收了2%
 *             回收的垃圾更不上产生垃圾的节奏
 *             垃圾回收器直接罢工
 * @date created in 2020/12/22 23:17
 */
public class GCOverHeadLimitExceededDemo {

    //java.lang.OutOfMemoryError: GC overhead limit exceeded
    public static void main(String[] args) {
        int i = 0;
        List<String> li = new ArrayList<>();

        try {
            while (true){
                li.add(String.valueOf(++i).intern()); //一直在GC
            }
        } catch (Throwable e) {
            System.out.println("******* i :" + i);  //******* i :145972
            e.printStackTrace();
            throw  e;
        }
    }
}
