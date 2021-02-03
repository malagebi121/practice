package com.embrace.practice.lockdemo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author embrace
 * @describe CyclicBarrier的字面意思是可循环(Cyclic)
 *     使用的屏障(barrier).它要做的事情是,让一组线程到达一个屏障(也可以叫做同步点)时被阻塞,
 *     直到最后一个线程到达屏障时,屏障才会开门,所有被屏障拦截的线程才会继续干活,
 *     线程进入屏障通过CyclicBarrier的await()方法.
 *
 *     CyclicBarrier  是做线程加法 ，  countDownLatch 是做减法
 *
 *     例子：集齐七颗龙珠，召唤神龙
 * @date created in 2020/12/20 11:47
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(7,() -> {
            System.out.println("七颗龙珠集齐了，召唤神龙");
        });

        for(int i = 1; i <= 7; i++){
            final int temp = i;
            new Thread(() ->{
                System.out.println(Thread.currentThread().getName() + "：第" + temp + "颗龙珠拿到了");
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf("线程" + temp)).start();
        }
    }
}
