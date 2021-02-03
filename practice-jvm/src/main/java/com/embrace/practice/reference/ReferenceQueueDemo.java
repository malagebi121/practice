package com.embrace.practice.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author embrace
 * @describe   引用队列
 * @date created in 2020/12/22 22:03
 */
public class ReferenceQueueDemo {
    public static void main(String[] args) {
        Object o = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        WeakReference<Object> weakReference = new WeakReference<>(o, referenceQueue); // 弱引用
        o = null;
        System.out.println(weakReference.get()); //java.lang.Object@68f7aae2
        System.out.println(referenceQueue.poll());//null
        System.gc();
        //gc 之后弱引用为null， referenceQueue 队列放入即将finalize() 的引用，先保存一下下。
        // 用于后面的处理，比如spring aop 的后置通知等
        System.out.println("=========   gc   =============");
        System.out.println(weakReference.get()); // null
        System.out.println(referenceQueue.poll());// java.lang.ref.WeakReference@4f47d241
    }
}
