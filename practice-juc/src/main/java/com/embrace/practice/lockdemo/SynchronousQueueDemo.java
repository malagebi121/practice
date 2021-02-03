package com.embrace.practice.lockdemo;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author embrace
 * @describe  不包元素，来一个数据消耗一个
 * @date created in 2020/12/20 20:10
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue();
        new Thread(() -> {
            try {
                synchronousQueue.put(1);
                System.out.println(Thread.currentThread().getName() + "  添加数据 " + 1);
                TimeUnit.SECONDS.sleep(2);

                synchronousQueue.put(2);
                System.out.println(Thread.currentThread().getName() + "  添加数据 " + 2);
                TimeUnit.SECONDS.sleep(2);

                synchronousQueue.put(3);
                System.out.println(Thread.currentThread().getName() + "  添加数据 " + 3);
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, String.valueOf("线程A")).start();

        //生产和消费个数要相同
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "  拿取数据据 " + synchronousQueue.take());
                System.out.println(Thread.currentThread().getName() + "  拿取数据据 " + synchronousQueue.take());
                System.out.println(Thread.currentThread().getName() + "  拿取数据据 " + synchronousQueue.take());
                //生产和消费个数要相同，不然程序不会停
//                System.out.println(Thread.currentThread().getName() + "  拿取数据据 " + synchronousQueue.take());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, String.valueOf("线程B")).start();

    }
}
