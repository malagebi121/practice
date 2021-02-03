package com.embrace.practice.reference;

import java.lang.ref.WeakReference;

/**
 * @author embrace
 * @describe   弱引用，只要发生GC 就回收，不管内存够不够
 *
 * @date created in 2020/12/22 21:28
 */
public class WeakReferenceDemo {
    public static void main(String[] args) {
        Object o = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o); // 弱引用
        o = null;
        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get()); // null

    }
}
