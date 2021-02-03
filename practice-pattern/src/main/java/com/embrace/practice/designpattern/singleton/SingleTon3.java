package com.embrace.practice.designpattern.singleton;

/**
 * @author embrace
 * @describe  静态内部类写法，静态类的属性就是instance
 *
 * 外部类装载的售后 内部类不会加载
 * 调用时候会加载，这种线程安全没问题
 *
 * @date created in 2021/1/13 11:38
 */
public class SingleTon3 {
    static class InnerClass{
        private static final SingleTon3 instance = new SingleTon3();
    }
    private SingleTon3(){}
    public static SingleTon3 getInstance(){
        return InnerClass.instance;
    }
    public static void main(String[] args) {
        SingleTon3 instance1 = SingleTon3.getInstance();
        SingleTon3 instance2 = SingleTon3.getInstance();
        System.out.println(instance1 == instance2);
    }
}
