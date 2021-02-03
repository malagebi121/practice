package com.embrace.practice.arithmetic;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author embrace
 * @describe  算法题目：
 *      A > B > C 三个线程打印轮询
 *      A  1次 ， B 2次， C 3次
 *      循环10次
 *
 *      用ReentrantLock的绑定多个条件的condition
 * @date created in 2020/12/20 23:16
 */
class ShareSource{
    private int num = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print1() throws Exception {
        lock.lock();
        try{
            while( num != 1){
                c1.await();
            }
            System.out.println("A");
            num  = 2;
            c2.signal();
        }finally {
            lock.unlock();
        }
    }

    public void print2() throws Exception {
        lock.lock();
        try{
            while( num != 2){
                c2.await();
            }
            for (int  i = 0;i < 2; i++){
                System.out.println("B");
            }
            num  = 3;
            c3.signal();
        }finally {
            lock.unlock();
        }
    }

    public void print3() throws Exception {
        lock.lock();
        try{
            while( num != 3){
                c3.await();
            }
            num  = 1;
            for (int  i = 0;i < 3; i++){
                System.out.println("C");
            }
            c1.signal();
        }finally {
            lock.unlock();
        }
    }
}


public class SyncAndReentrantLockDemo {

    public static void main(String[] args) {
        ShareSource shareSource = new ShareSource();

        new Thread( () -> {
            for (int  i = 0; i < 10; i++){
                try {
                    shareSource.print1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread( () -> {
            for (int  i = 0; i < 10; i++) {
                try {
                    shareSource.print2();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread( () -> {
            for (int  i = 0; i < 10; i++) {
                try {
                    shareSource.print3();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
