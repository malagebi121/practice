package com.embrace.practice.threadpool;

import java.util.concurrent.*;

/**
 * @author embrace
 * @describe  线程池拒绝策略
 *                 等待队列也已经排满了,再也塞不下新的任务了，同时，
 *                 线程池的max也到达了,无法接续为新任务服务
 *                 这时我们需要拒绝策略机制合理的处理这个问题
 *
 *
 * @date created in 2020/12/21 22:32
 */
public class MyRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        //ThreadPoolExecutor 里面有四种拒绝策略
        //默认拒绝策略
//        throw new RejectedExecutionException("Task " + r.toString() +
//                " rejected from " +
//                e.toString());


    }
}
