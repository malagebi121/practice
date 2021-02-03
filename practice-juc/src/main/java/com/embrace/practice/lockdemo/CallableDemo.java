package com.embrace.practice.lockdemo;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author embrace
 * @describe   RunnableFuture 继承了 Runnable 和 Future
 *             FutureTask 实现了 RunnableFuture，同时又 Callable的构造方法
 *             相当于 FutureTask  从中间起链接作用, futureTask接收的callable 传入 thread就可以了
 *             FutureTask 相当于是一个中间人,这是一个适配模式，接口相当于一个声明，类可以实现多个接口
 *
 *             值得思考的问题，有了 Runnable 为什么还要有 Callable  呢？
 *                   做不了（耗时较长）的任务可以做其他的，不用阻塞在这里，一道题做不来可以先做其他的（新线程去做），
 *                       在最后要汇总才等待，使得main线程耗时缩短。
 *                   高并发最牛逼的不是加锁，而是控制
 *                  并发和异步需要 Callable
 *
 * @date created in 2020/12/21 20:25
 */
class MyThreadRunnable implements Runnable{
    @Override
    public void run() {

    }
}

class MyThreadCallable implements Callable{
    @Override
    public Object call() throws Exception {
        System.out.println("AA 进来了");
        TimeUnit.SECONDS.sleep(5);
        return 1024;
    }
}

public class CallableDemo {
    public static void main(String[] args) throws Exception {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThreadCallable());
//        futureTask.run();
        new Thread(futureTask,"AA").start();
        new Thread(futureTask,"BB").start(); // 同一个future会线程复用，不会再去复用
        //这个简单的可以直接在 main 里面计算
        System.out.println("=======  主线程计算  ========");
        int b = 100;
        //这里阻main线程 , 假如future 要计算 5 秒， 其他4秒, 如果是以前需要9秒，现在只需要五秒

//        while (!futureTask.isDone()){
//            //如果task 没完会一直自旋等待在这里,像自旋锁
//        }

        int a = futureTask.get();
        int c = futureTask.get();
        //最后结果合并
        System.out.println(a + b + c);
    }
}
