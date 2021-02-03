package com.embrace.practice.designpattern.singleton;

/**
 * @author embrace
 * @describe  懒汉模式（ 不加synchronized  是线不安全）
 * 优点：线程安全，达到懒加载效果
 *
 * 缺点：但是每次进来的时候都要同步，效率很低
 * @date created in 2021/1/13 11:26
 */
public class SingleTon2 {
    private static SingleTon2 instance;
    private SingleTon2(){}
    //同步  , 放在代码块线程有问题
    public synchronized static SingleTon2 getInstance(){
        if(instance == null){
            //  这里加锁会有问题 ，如果都进入了这里就会出问题，不能使用
            instance = new SingleTon2();
        }
        return instance;
    }
    public static void main(String[] args) {
        SingleTon2 instance1 = SingleTon2.getInstance();
        SingleTon2 instance2 = SingleTon2.getInstance();
        System.out.println(instance1 == instance2);
    }
}
