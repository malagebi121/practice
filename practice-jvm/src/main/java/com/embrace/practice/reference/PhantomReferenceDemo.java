package com.embrace.practice.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author embrace
 * @describe    虚引用，顾名思义形同虚设
 *                 如果一个对象仅仅持有一个虚引用，那么他就和没有任何引用一样，随时都可能被垃圾回收
 *              虚引用不能单独使用也不能用他的 get() 获取对象，一直为 null 。必须联合 ReferenceQueue 才能使用
 *
 *              虚引用的作用主要用于跟踪对象被 finalize() 之前的各种状态
 *              换句话说，虚引用能让对象在被垃圾回收器收集的时候收到系统通知，或者后续添加进一步处理
 * @date created in 2020/12/22 22:10
 */
public class PhantomReferenceDemo {
    public static void main(String[] args) {
        Object o = new Object();
        ReferenceQueue<Object> queue = new ReferenceQueue();
        PhantomReference<Object> phantomReference = new PhantomReference<>(o, queue); // 弱引用
        o = null;
        System.out.println(phantomReference.get()); // null
        System.out.println(queue.poll()); // null

        System.gc(); //加入引用队列

        System.out.println(phantomReference.get()); // null
        System.out.println(queue.poll()); //java.lang.ref.PhantomReference@68f7aae2

    }
}
