package com.embrace.practice.reference;

import org.omg.CORBA.OBJ_ADAPTER;

/**
 * @author embrace
 * @describe    引用在 java.lang.ref 下面
 *              当内存不足时候，jvm开始回收，对于强引用对象，就算是OOM了都不会回收
 *                   强引用使我们最常见的引用，只要有强引用还指向对象，就表示对象还活着，垃圾收集器不会回收该对象
 *              把对象赋值给一个引用变量，这个引用变量就是强引用，当一个对象别强引用变量引用时，对象就是可达的，
 *              是不可能被垃圾回收机制回收的，即使该对象以后永远不会被jvm回收，强引用时造成内存泄漏的主要原因。
 *                  对于强引用，只要超过其作用于，对齐赋值为null，就可以别垃圾回收器收集了
 * @date created in 2020/12/22 20:46
 */
public class StrongReferenceDemo {
    public static void main(String[] args) {
        Object object1 = new Object();
        Object object2 = object1;  //object2 指向 object1 对象，就算object1 指向null，但是 object2 指向 对象
        object1 = null;
        System.gc();
        System.out.println(object2); //java.lang.Object@68f7aae2   不会为null


    }
}
