package com.embrace.practice.deadlock;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author embrace
 * @describe
 *     四个必要条件
 *        互斥条件：进程对所分配到的资源不允许其他进程进行访问，若其他进程访问该资源，只能等待，直至占有该资源的进程使用完成后释放该资源
 *        请求和保持条件：进程获得一定的资源之后，又对其他资源发出请求，但是该资源可能被其他进程占有，此事请求阻塞，但又对自己获得的资源保持不放
 *        不可剥夺条件：是指进程已获得的资源，在未完成使用之前，不可被剥夺，只能在使用完后自己释放
 *        环路等待条件：是指进程发生死锁后，必然存在一个进程–资源之间的环形链
 *
 *     产生的主要原因
 *         系统资源不足
 *         进行运行推进不合理
 *         资源分配不当
 *
 *    死锁定位
 *      查看进程
 *          ps -ef|grep XXX
 *          jps
 *
 *          windows 下面 jps -lj
 *
 *      打印信息
 *          jstack pid
 *          jstat
 *
 *      jps  定位
 *      jstack pid
 *
 *
 *
 * Java stack information for the threads listed above:
 * ===================================================
 * "threadBBB":
 *         at com.embrace.practice.deadlock.HoldLockThread.run(DeadLockDemo.java:53)
 *         - waiting to lock <0x000000076ba5f4a8> (a java.lang.String)
 *         - locked <0x000000076ba5f4e0> (a java.lang.String)
 *         at java.lang.Thread.run(Thread.java:748)
 * "threadAAA":
 *         at com.embrace.practice.deadlock.HoldLockThread.run(DeadLockDemo.java:53)
 *         - waiting to lock <0x000000076ba5f4e0> (a java.lang.String)
 *         - locked <0x000000076ba5f4a8> (a java.lang.String)
 *         at java.lang.Thread.run(Thread.java:748)
 *
 * Found 1 deadlock.
 *
 *
 *
 * @date created in 2020/12/22 0:08
 */
class HoldLockThread implements Runnable{

    private String lockA ;
    private String lockB ;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有锁" + lockA + "尝试获得" + lockB);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有锁" + lockB + "尝试获得" + lockA);
            }
        }

    }
}


public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldLockThread(lockA, lockB), "threadAAA").start();
        new Thread(new HoldLockThread(lockB, lockA), "threadBBB").start();

    }
}
