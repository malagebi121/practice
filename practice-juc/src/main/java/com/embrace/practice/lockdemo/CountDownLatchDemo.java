package com.embrace.practice.lockdemo;

import com.embrace.practice.enums.CountryEnum;

import java.util.concurrent.CountDownLatch;

/**
 * @author embrace
 * @describe  门闩，countDownLatch是在java1.5被引入，
 *    跟它一起被引入的工具类还有CyclicBarrier、Semaphore、concurrentHashMap和BlockingQueue
 *    countDownLatch这个类使一个线程等待其他线程各自执行完毕后再执行。
 *    让一些线程阻塞直到另外一些完成后才被唤醒
 *    是通过一个计数器来实现的，计数器的初始值是线程的数量。每当一个线程执行完毕后，计数器的值就-1，
 *    当计数器的值为0时，表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了。
 *
 *    其中单个方法最重要：
 *    //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
 *    public void await() throws InterruptedException { };
 *    //和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
 *    public boolean await(long timeout, TimeUnit unit) throws InterruptedException { };
 *    //将count值减1
 *    public void countDown() { };
 *    例子： 六国被灭，秦国统一了
 *          春夏秋冬，一年过去了
 *
 * @date created in 2020/12/19 23:24
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(6);
        for(int i = 1; i <= 6; i++){
            new Thread( () -> {
                System.out.println(Thread.currentThread().getName() + " 被灭了");
                latch.countDown();
            }, CountryEnum.getEnumByKey(i).getValue()).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " 秦国统一了");
    }

}
