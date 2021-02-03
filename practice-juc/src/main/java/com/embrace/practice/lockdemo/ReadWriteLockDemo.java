package com.embrace.practice.lockdemo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author embrace
 * @describe
 *    多个线程可以同时读取同一资源，提供并发量，比如火车站的显示大屏
 *    但是在写入修改时候只能由一个线程是操作，在这期间其读线程和写线程被排斥
 *    总结：
 *      读读同时可以
 *      读写同时不可以
 *      写写同时不可以
 *    没有加任何锁前存在大量问题
 *
 *    写操作： 原子 加 独占 ，整个过程应该是不被打断的原子过程
 *
 * @date created in 2020/12/19 22:15
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        ReadWriteLockCache cache = new ReadWriteLockCache();

        //写线程
        for (int i = 1; i <= 2; i++){
            final int temp = i;
            new Thread(() -> {
                cache.write(temp + "", temp);
            },String.valueOf("写入线程"+i)).start();
        }

        //读线程
        for (int i = 1; i <= 2; i++){
            final int temp = i;
            new Thread(() -> {
                cache.read(temp + "");
            },String.valueOf("读取线程"+i)).start();
        }
    }



}

class ReadWriteLockCache{
    //缓存数据优先想到hash
    private Map<String,Object> map = new HashMap<>();

    //加入锁，这个读取也只能一个来读，有问题，效率太低
//    ReentrantLock lock = new ReentrantLock();

    // ReadWriteLock 读写锁
    ReadWriteLock lock = new ReentrantReadWriteLock();

    // 解锁放在 finally 里面
    public void  write(String  key, Object value){
        lock.writeLock().lock();
        System.out.println(Thread.currentThread().getName() + "正在写入：" + key);
        //模拟写入三秒
        try {
            TimeUnit.SECONDS.sleep(3);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }
    }

    public void  read(String  key){
        System.out.println(Thread.currentThread().getName() + "正在读取");
        //模拟读取一秒
        lock.readLock().lock();
        try {
//            TimeUnit.SECONDS.sleep(1);
            Object value = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取完成： " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.readLock().unlock();
        }
    }

}
