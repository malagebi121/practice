package com.embrace.practice.designpattern.singleton;

/**
 * @author embrace
 * @describe 枚举 , Effective java 上推荐
 * @date created in 2021/1/13 12:57
 */
public enum SingleTon5 {
    INSTANCE;
    public void run(){
        System.out.println("hello kitty");
    }
    public static void main(String[] args) {
        SingleTon5 instance1 = SingleTon5.INSTANCE;
        SingleTon5 instance2 = SingleTon5.INSTANCE;
        System.out.println(instance1 == instance2);
        instance1.run();
        instance2.run();
    }
}
