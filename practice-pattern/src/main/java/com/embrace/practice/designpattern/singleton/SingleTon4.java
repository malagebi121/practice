package com.embrace.practice.designpattern.singleton;

/**
 * @author embrace
 * @describe  double lock check  dlc 双重检查
 * volatile 防止类加载指令重排，因为类对象有地址的时候可能类并没有完全初始化
 * synchronized 之前判断反之阻塞， 里面判断反之两个进入里面
 * 线程安全，效率高，延时，防止多次实例化
 * @date created in 2021/1/13 11:38
 */
public class SingleTon4 {
    // 保证 instance 改变的可见性，防止类加载指令重排
    private static volatile SingleTon4 instance;
    private SingleTon4(){}
    public static SingleTon4 getInstance(){
        //第一次检查
        if (instance == null){
            synchronized (SingleTon4.class){
                //第二次检查
                if(instance == null){
                    instance = new SingleTon4();
                }
            }
        }
        return instance;
    }
}
