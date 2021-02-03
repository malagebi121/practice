package com.embrace.practice.designpattern.observer.improve;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/14 11:37
 */
public class SendMilk {
    public static void main(String[] args) {
        MilkStation milkStation = new MilkStation();
        XiaoMingFamily family = new XiaoMingFamily("小明家");
        milkStation.registerObserver(family);
        milkStation.notifyObserver("开始发送牛奶了额");
    }
}
