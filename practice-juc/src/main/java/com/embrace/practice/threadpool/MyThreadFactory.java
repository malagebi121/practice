package com.embrace.practice.threadpool;

import java.util.concurrent.ThreadFactory;

/**
 * @author embrace
 * @describe  自己的线程工厂
 * @date created in 2020/12/21 22:26
 */
public class MyThreadFactory implements ThreadFactory{
    @Override
    public Thread newThread(Runnable r) {



          //Executor 里面的
//        Thread t = new Thread(group, r,
//                namePrefix + threadNumber.getAndIncrement(),
//                0);
//        if (t.isDaemon())
//            t.setDaemon(false);
//        if (t.getPriority() != Thread.NORM_PRIORITY)
//            t.setPriority(Thread.NORM_PRIORITY);
//        return t;


        Thread thread = new Thread();
        thread.setName("我自己的工厂");
        thread.setDaemon(false);//守护线程
        thread.setPriority(2);//优先级别
        return new Thread(r, "");
    }
}
