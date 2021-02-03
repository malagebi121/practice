package com.embrace.practice.lockdemo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author embrace
 * @describe
 * @date created in 2020/12/20 12:08
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        for(int i = 1; i <= 4; i++){
            final int temp = i;
            new Thread(() -> {
                try {
                    semaphore.acquire(); // semaphore.tryQuire();
                    System.out.println(Thread.currentThread().getName() + "抢到了车位");
                    TimeUnit.SECONDS.sleep(4);
                    System.out.println(Thread.currentThread().getName() + "离开了车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();

                }
            },String.valueOf("线程" + i)).start();
        }
    }
}
