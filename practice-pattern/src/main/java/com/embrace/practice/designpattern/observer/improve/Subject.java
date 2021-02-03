package com.embrace.practice.designpattern.observer.improve;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/14 11:20
 */
public interface Subject {
    //注册通知者(观察者)
    boolean registerObserver(Observer observer);
    // 移除
    boolean removeObserver(Observer observer);
    //通知
    void notifyObserver(String msg);
}
