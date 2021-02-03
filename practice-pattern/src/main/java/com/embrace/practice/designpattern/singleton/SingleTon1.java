package com.embrace.practice.designpattern.singleton;

/**
 * @author embrace
 * @describe  恶汉模式，初始化类的时候就有了（两种写法）
 *
 *  Runtime 类里面的用到
 *  private static Runtime currentRuntime = new Runtime();
 *
 * 优点：写法简单，类加载的时候完成实例化，避免了多线程同步问题
 * 缺点： 类加载时候实例化，没有达到 lazyLoad 的效果，如果以后没有用到会造成内存浪费
 * @date created in 2021/1/13 11:18
 */
public class SingleTon1 {
    // 静态代码块写法
    static {
        instance = new SingleTon1();
    }
    private static SingleTon1 instance;
//    //属性构建写法
//    private static final SingleTon1 instance = new SingleTon1();
//
    //构造方法私有化
    private SingleTon1(){}
    //静态方法获取实例
    public static SingleTon1 getInstance(){
        return instance;
    }
    public static void main(String[] args) {
        SingleTon1 instance1 = SingleTon1.getInstance();
        SingleTon1 instance2 = SingleTon1.getInstance();
        System.out.println(instance1 == instance2);
    }
}
