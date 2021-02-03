package com.embrace.practice.threadpool;

import java.util.concurrent.*;

/**
 * @author embrace
 * @describe
 *            四种拒绝策略
 *                  AbortPolicy(默认):直接抛出RejectedException异常阻止系统正常运行
 *                  CallerRunsPolicy:"调用者运行"一种调节机制,该策略既不会抛弃任务,也不会抛出异常,而是让调用者的线程自己处理
 *                  DiscardOldestPolicy:抛弃队列中等待最久的任务,然后把当前任务加入队列中尝试再次提交
 *                  DiscardPolicy:直接丢弃任务,不予任何处理也不抛出异常.如果允许任务丢失,这是最好的拒绝策略
 *            实际情况下一个也不用，用自己的（alibaba 规范），自己动手，丰衣足食
 * @date created in 2020/12/21 23:39
 */
public class RejectedExecutionHandlerDemo {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor =  new ThreadPoolExecutor(
                2,
                5,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3), //BlockingQueue<Runnable> , 阻塞队列，数量
                Executors.defaultThreadFactory(),   //可以用默认的 Executors.defaultThreadFactory()
//                new ThreadPoolExecutor.AbortPolicy()  //默认  new ThreadPoolExecutor.AbortPolicy()   工作中一个不用，自己重写
                new ThreadPoolExecutor.CallerRunsPolicy()  // 谁传入的由谁执行
        );

        try {
            for (int i = 1; i <= 10; i++) {
                threadPoolExecutor.execute(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }
    }
}
