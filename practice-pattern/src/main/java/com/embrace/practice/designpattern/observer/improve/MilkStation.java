package com.embrace.practice.designpattern.observer.improve;

import java.util.ArrayList;
import java.util.List;

/**
 * @author embrace
 * @describe  牛奶站，发送牛奶
 * @date created in 2021/1/14 11:19
 */
public class MilkStation implements Subject{

    private List<Observer> observers;

    public MilkStation() {
        observers = new ArrayList<>();
    }

    @Override
    public boolean registerObserver(Observer observer) {
        observers.add(observer);
        System.out.println(observer.getName() +" 注册成功");
        return true;
    }

    @Override
    public boolean removeObserver(Observer observer) {
        boolean result = observers.remove(observer);
        System.out.println("移除" + (result ? "成功" : "失败"));
        return result;
    }

    @Override
    public void notifyObserver(String msg) {
        for (Observer observer : observers) {
            boolean  result =  observer.notice(msg);
            System.out.println(observer.getName() +" 接收消息 "+ (result ? "成功" : "失败"));
        }
    }
}
