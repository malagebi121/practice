package com.embrace.practice.proandcon;

import java.sql.Timestamp;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author embrace
 * @describe    阻塞队列实现生产和消费模式
 * @date created in 2020/12/21 19:43
 */
class MySource{
    //总开关
    private volatile boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private BlockingQueue<String> blockingQueue = null;

    //spring 里面两种依赖注入，构造方法注入 和 setter注入
    public MySource(BlockingQueue blockingQueue){
        this.blockingQueue = blockingQueue;
        System.out.println("传入的是：" + blockingQueue.getClass().getName());
    }

    public void myProvider() throws Exception{
        String data;
        boolean returnVal;
        while (flag){
            data = atomicInteger.incrementAndGet() + "";
            returnVal = blockingQueue.offer(data, 2 , TimeUnit.SECONDS);
            if(returnVal){
                System.out.println(Thread.currentThread().getName() + "插入队列成功：" + data);
            }else{
                System.out.println("插入队列失败：" + data);
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println("停止工作");
    }

    public void myConsumer() throws Exception{
        String result;
        boolean returnVal;
        while (flag){
            result = blockingQueue.poll( 2 , TimeUnit.SECONDS);
            if(null == result && !"".equals(result)){
                flag =  false;
                System.out.println("超过两秒没有获取到数据，退出");
                System.out.println();
                System.out.println();
                return;
            }
            TimeUnit.SECONDS.sleep(3);
            System.out.println(Thread.currentThread().getName() +"消费队列成功：" + result);
        }
        System.out.println("停止工作");
    }

    public void stop(){
        this.flag = false;
    }

}



public class BlockQueue_providerAndConsumer {
    public static void main(String[] args) throws InterruptedException {
        MySource mySource = new MySource(new ArrayBlockingQueue(10));
        new Thread(() -> {
            try {
                mySource.myProvider();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"provider1").start();

        new Thread(() -> {
            try {
                mySource.myProvider();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"provider2").start();

        new Thread(() -> {
            try {
                mySource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"consumer").start();


        TimeUnit.SECONDS.sleep(10);

        //停止全部线程
        mySource.stop();

    }
}
