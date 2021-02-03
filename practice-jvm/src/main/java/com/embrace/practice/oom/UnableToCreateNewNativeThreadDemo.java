package com.embrace.practice.oom;

import java.util.concurrent.TimeUnit;

/**
 * @author embrace
 * @describe  Exception in thread "main" java.lang.OutOfMemoryError: unable  to create new native thread
 * 高并发请求服务器时候，有时候会爆出oom
 * 准确地讲 native thread 与对应的平台有关
 * 比如订单，物理等服务应用
 *
 * 发生原因
 *      系统应用创建了太多的线程，一个进程创建多个进程，超过系统承载的极限
 *      服务器不允许一个进程创建太多线程，linux 默认一个进程不能超过1024个线程(非 root 用户在 linux限制 1024， 总共八九百个就直接挂了，可以直接改配置 )
 *      应用创建的线程超过了这个，就会oom
 *
 *解决办法：
 *    限制一个应用的线程数
 *    修改linux的配置，扩大最大的线程承载数
 *
 * @date created in 2020/12/22 23:32
 */
public class UnableToCreateNewNativeThreadDemo {
    public static void main(String[] args) {
        for(int  i = 0;   ; i++ ){
            System.out.println(" i = " + i);
            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, i + "").start();
        }
    }
}
