package com.embrace.practice.lockdemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author embrace
 * @describe 自旋锁Demo
 *           自旋锁表示获取锁的线程不会立即阻塞，而是采用循环方式去获取锁
 *           这样好处是减少了线程上下文切换的开销，坏处是可能一直无效循环，cpu空转
 *           通过cas完成自旋锁
 * @date created in 2020/12/16 22:38
 */
public class SpinLockDemo {

    //线程原子类的载体
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + ">>>当前线程，加锁");
        //第一进来为null，直接赋值为true，取反false，进入代码
        while (!atomicReference.compareAndSet(null, thread)){

        }
    }

    public void unLock(){
        Thread thread = Thread.currentThread();
        //第二次来又把值赋值为 null ， 下个要lock的就会进来，不然会一直 while(true){ 空转}
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + ">>>当前线程，解锁啦");
    }

    public static void main(String[] args) {
        SpinLockDemo lock = new SpinLockDemo();

        //aaa线程先跑，并且拿到锁
        new Thread(() -> {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " >>> 拿到锁了");
            System.out.println(Thread.currentThread().getName() + " >>>  睡眠5秒");
            try {
                TimeUnit.SECONDS.sleep(5l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unLock();
            System.out.println(Thread.currentThread().getName() + " >>> 解锁了");
        },"aaa线程").start();

        try { TimeUnit.SECONDS.sleep(3l); } catch (InterruptedException e) { e.printStackTrace();}

        //bbb线程
        new Thread(() -> {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " >>> 拿到锁了");
            lock.unLock();
            System.out.println(Thread.currentThread().getName() + " >>> 解锁了");
        },"bbb线程").start();

    }


}
