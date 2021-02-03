package com.embrace.practice.reference;

import java.lang.ref.SoftReference;

/**
 * @author embrace
 * @describe  软引用  设置 VM options 模拟内存不足   -Xms5m -Xmx5m -XX:+PrintGCDetails
 *            软引用是相对于强应用较弱化的引用
 *            内存充足情况下不会被回收
 *            内存不足的时候将会被回收
 *            软引用应用在对内存敏感的程序中，比如高速缓存， mybatis的缓存大量使用软引用和弱引用
 *
 *            假如一个应用需要大量读取图片：
 *                 每次都从硬盘加载太慢
 *                 全部加载到内存容易oom
 *            那么可以设计一个 HashMap 来保存图片路径 和 图片对象的软引用或者弱引用，内存不够或者垃圾回收之后清理图片，不会造成oom
 *            Map<String, SoftReference<Bitmap>> imageCache = new HashMap<>();
 *
 * @date created in 2020/12/22 21:07
 */
public class SoftReferenceDemo {

    //内存充足情况
    public static void soft_memory_enough(){
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1); //这两个指向同一个
        System.out.println(softReference.get());

        o1 = null;
        System.gc();
        System.out.println(softReference.get());
    }

    //内存不足的情况
    public static void soft_memory_notEnough(){
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1); //这两个指向同一个
        System.out.println(softReference.get());

        o1 = null;
        try{
            byte[] b = new byte[10 * 1024 * 1024];
        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            System.gc();
            System.out.println(softReference.get()); //内存不够用，报null
        }
    }


    public static void main(String[] args) {
        soft_memory_enough();
        soft_memory_notEnough();
    }
}
