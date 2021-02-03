package com.embrace.practice.lockdemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author embrace
 * @describe  ArrayBlockingQueue 基于数组的有界数组阻塞队列
 *            LinkedBlockingQueue  基于链表的阻塞队列
 *            SynchronousQueue   不储存元素dev阻塞队列，走一个允许来一个
 *            PriorityBlockingQueue 支持优先级排序的无界阻塞队列
 *            DelayQueue  使用优先级队列实现的延迟无界阻塞队列
 *            LinkedTransferQueue 由链表结构组成的无界阻塞队列.
 *            LinkedBlockingDeque 由链表结构组成的双向阻塞队列.
 *
 *            阻塞队列有没有好的一面
 *                  就餐等待，医院，银行，火车站购票
 *            一定要阻塞该怎么办，如何管理
 *                  队列空的时候阻塞
 *                  队列满的时候放入阻塞
 *
 *           blockingQueue 我们不需要什么时候去阻塞线程，什么时候去唤醒线程，直接包办
 *                  抛出异常       特殊值      阻塞          超时
 *           插入    add(v)       offer(v)   put(v)       offer( V v,time , unit )
 *           移除    remove()     poll()     tak()        poll(time, unit)
 *           检查    element()    peek()      无
 *
 * @date created in 2020/12/20 13:40
 */
public class BlockingQueueDemo {
    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);

//        System.out.println("===========    抛出异常测试      ==============");
//        //抛出异常
//        System.out.println(queue.add(1));
//        System.out.println(queue.add(2));
////        queue.add(1);  //   超出容量报错 Queue full
//
//        System.out.println(queue.element()); // 这个没有元素也会报错,用于检查即将出队的元素
//        System.out.println(queue.remove());
////        System.out.println(queue.remove());
//        System.out.println(queue.remove()); // 没有值硬取出 transport: 'socket'

        System.out.println("===========    特殊值      ==============");
//        System.out.println(queue.offer(1));
//        System.out.println(queue.offer(2));
//        System.out.println(queue.offer(3)); //  print false 这个添加不会报错，但是添加不进去
//
//        System.out.println(queue.poll());
//        System.out.println(queue.poll());
//        System.out.println(queue.poll()); //print  null 这个添加不会报错，但是那不到值


        System.out.println("===========    阻塞      ==============");
//        try {
//            queue.put(1);
//            queue.put(2);
////            queue.put(3);  //  阻塞起一直不停止
//
//            System.out.println(queue.take());
//            System.out.println(queue.take());
////            System.out.println(queue.take()); //阻塞起一直不停止
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println("===========   超时      ==============");
        try {
            System.out.println(queue.offer(1, 1, TimeUnit.SECONDS));
            System.out.println(queue.offer(2, 1, TimeUnit.SECONDS));
//            System.out.println(queue.offer(3, 1, TimeUnit.SECONDS)); // false
            System.out.println(queue.poll(1, TimeUnit.SECONDS));
            System.out.println(queue.poll(1, TimeUnit.SECONDS));
//            System.out.println(queue.poll(1, TimeUnit.SECONDS)); // null
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
