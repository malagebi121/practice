package com.embrace.practice.proandcon;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author embrace
 * @describe   ReentrantLock  加上 condition 的await() , signalAll()  的消费者模式
 *
 * 题目： 初始值为 0 ，一个线程加一，一个减一，交替操作
 *
 *       1. 线程   操作  资源类
 *       2. 判断  干活   通知
 *       3. 防止虚假唤醒  (判断要用 while 不能用 if)
 * @date created in 2020/12/20 22:24
 */
public class LockCondition_providerAndConsumer {

    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(() -> {
            for(int  i = 0; i < 5; i++){
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"生产者").start();

        new Thread(() -> {
            for(int  i = 0; i < 5; i++){
                try {
                    shareData.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"消费者").start();
        //多个线程必须用while 判断
//        new Thread(() -> {
//            for(int  i = 0; i < 5; i++){
//                try {
//                    shareData.increment();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        },"生产者1").start();
//
//        new Thread(() -> {
//            for(int  i = 0; i < 5; i++){
//                try {
//                    shareData.decrement();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        },"消费者1").start();
    }
}

/**
 * 资源类
 */
class ShareData{
    private int  num;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //生产者
    public void  increment() throws Exception {
        lock.lock();
        try{
            while (num != 0){
                //不能生产
                condition.await();
            }
            //干活
            num ++;
            System.out.println(Thread.currentThread().getName() + "\t" + num);
            condition.signalAll();
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }


    //生产者
    public void  decrement() throws Exception {
        lock.lock();
        try{
            while (num == 0){
                //不能生产
                condition.await();
            }
            //干活
            num --;
            System.out.println(Thread.currentThread().getName() + "\t" + num);
            condition.signalAll();
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
