package com.embrace.practice.threadpool;

import java.util.concurrent.*;

/**
 * @author embrace
 * @describe  再牛笔的代码也要依赖于硬件，如果只有单核，线程池也没有任何意义
 *            1.为什么要用线程池？
 *                  线程池主要的工作就是控制运行线程的数量，处理过程中将任务放入队列
 *               然后线程创建后运行这些任务，如果线程运行的数量达到了最大数量，新来的任务
 *               将会放入等待队列中，等其他任务执行完毕，在从任务队列拿取运行
 *                  特点
 *                      线程复用，控制最大并发数，管理线程
 *                  优势（线程池，数据库连接池子都差不多）
 *                      降低资源消耗，通过一创建的线程重复利用执行任务，降低了线程创建和销毁的过程
 *                      提高响应速度，任务不需要等待新建线程就可以立即执行（前提是有空闲线程）
 *                      提高线程的可管理性.线程是稀缺资源,如果无限的创阿金,不仅会消耗资源,还会较低系统的稳定性,
 *                         使用线程池可以进行统一分配,调优和监控.
 *            2.如何使用 ？
 *               new  ThreadPoolExecutor(...); 或者 线程池工具类 Executors(工厂模式)
 *
 *            3.线程池参数的含义？
 *                  线程池参数
 *                      corePoolSize
 *                          线程池中的常驻核心线程数
 *                          当任务来了之后就会安排核心线程去执行
 *                          当线程池中的线程数目达到corePoolSize后,就会把到达的任务放入到缓存队列当中
 *                      maximumPoolSize
 *                          线程池能够容纳同时执行的最大线程数,此值大于等于1，
 *                          maximumPoolSize  > corePoolSize , 阻塞队列满了之后，线程池树扩容
 *                          新建最大线程数 maximumPoolSize - corePoolSize
 *                      keepAliveTime
 *                          多余的空闲线程存活时间,当空间时间达到keepAliveTime值时,多余的线程会被销毁直到只剩下corePoolSize个线程为止
 *                          只有当线程池中的线程数大于corePoolSize时keepAliveTime才会起作用,知道线程中的线程数不大于corepoolSIze
 *                      TimeUnit
 *                          存活时间单位
 *                      BlockingQueue<Runnable>
 *                          挂载Runnable的阻塞队列，任务队列,被提交但尚未被执行的任务
 *
 *                  ThreadPoolExecutor里面还有两个 （注: 在alibaba规范中不能世界使用Executors创建线程池，要用这个）
 *                      ThreadFactory
 *                          线程工厂，表示生成线程池中工作线程的线程工厂,用户创建新线程,一般用默认即可
 *                      RejectedExecutionHandler
 *                          拒绝策略,表示当线程队列满了并且工作线程大于等于线程池的最大显示 数(maxnumPoolSize)时如何来拒绝.
 *
 *            4.线程池底层实现原理？
 *                  基于工厂模式 new  ThreadPoolExecutor(...) 中传入不同的参数获取不同含义的线程池，最后调用
 *                  重载构造方法 ThreadPoolExecutor(int corePoolSize,
 *                               int maximumPoolSize,
 *                               long keepAliveTime,
 *                               TimeUnit unit,
 *                               BlockingQueue<Runnable> workQueue,
 *                               ThreadFactory threadFactory,
 *                               RejectedExecutionHandler handler) 新建线程池
 *
 *                  ThreadPoolExecutor基于  BlockingQueue<Runnable>  管理挂载实现 Runnable 接口类的阻塞队列实现
 *
 *           5.生产中的线程核心数配置？
 *                 查看硬件核心数： Runtime.getRuntime().availableProcessors()
 *                 cpu密集型
 *                      cpu需要大量计算，没有阻塞，cpu一直全力运行
 *                      一般应该尽量减少线程池核心数， 一般为 cpu 核数 + 1 个的线程池
 *
 *                 io密集型
 *                      io线程并不是一直在执行任务，配置更多的线程 核心数 * 2
 *                      任务需要大量的阻塞，在单线程上运行会浪费大量的 cpu 运算能力
 *                          公式： cpu 核心数 / 1 -  阻塞系数   阻塞系数： 阻塞时间 / 总共执行时间
 *                          假如阻塞系数 = 0.9 , cpu 8 核 , 8 / 0.1 = 80 个
 *
 *
 *
 *
 *
 *           思维导图: https://processon.com/view/5edefa00e401fd1fd281d823
 *                    https://processon.com/view/5cc2bd36e4b0841b84410cc9
 *
 * @date created in 2020/12/21 21:14
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        //查看硬件核心数
        System.out.println(Runtime.getRuntime().availableProcessors());

        // Array Arrays    工具类
        //Collection Collection
        //Executor Executors
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1,
                1,
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(), //BlockingQueue<Runnable>
                new MyThreadFactory(),   //可以用默认的 Executors.defaultThreadFactory()
                new MyRejectedExecutionHandler()  //默认  new ThreadPoolExecutor.AbortPolicy()   工作中一个不用，自己重写
        );

        //创建一个定长线程池,可控制线程的最大并发数,超出的线程会在队列中等待
        //newFixedThreadPool创建的线程池corePoolSize和MaxmumPoolSize是 相等的,它使用的的LinkedBlockingQueue
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
//        try{
//            //处理中线程也可以复用
//            for(int i = 1; i <= 20; i++){
//                fixedThreadPool.submit( () -> {
//                    System.out.println(Thread.currentThread().getName() + "正在处理业务");
//                    try {
//                        TimeUnit.SECONDS.sleep(2);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                });
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally{
//            fixedThreadPool.shutdown();
//        }


        //创建一个单线程化的线程池,它只会用唯一的工作线程来执行任务,保证所有任务都按照指定顺序执行.
        //newSingleThreadExecutor将corePoolSize和MaxmumPoolSize都设置为1,它使用的的LinkedBlockingQueue
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
//        try{
//            //处理中线程也可以复用
//            for(int i = 1; i <= 20; i++){
//                singleThreadExecutor.submit( () -> {
//                    System.out.println(Thread.currentThread().getName() + "正在处理业务");
//                    try {
//                        TimeUnit.SECONDS.sleep(2);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                });
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally{
//            singleThreadExecutor.shutdown();
//        }


        // 创建一个可缓存线程池,如果线程池长度超过处理需要,可灵活回收空闲线程,若无可回收,则创建新线程.
        //newCachedThreadPool将corePoolSize设置为0MaxmumPoolSize设置为Integer.MAX_VALUE,
        // 它使用的是SynchronousQueue,也就是说来了任务就创建线程运行,如果线程空闲超过60秒,就销毁线程
        ExecutorService cachedThreadPool  =  Executors.newCachedThreadPool();
        try{
            //处理中线程也可以复用
            for(int i = 1; i <= 20; i++){
                cachedThreadPool.submit( () -> {
                    System.out.println(Thread.currentThread().getName() + "正在处理业务");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            cachedThreadPool.shutdown();
        }


    }
}
