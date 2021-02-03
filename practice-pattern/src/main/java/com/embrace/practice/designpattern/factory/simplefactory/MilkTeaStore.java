package com.embrace.practice.designpattern.factory.simplefactory;

/**
 * @author embrace
 * @describe   奶茶店 ， 开始订购奶茶
 *
 *  违反了OCP 原则 ，如果增加额另一种奶茶，就必须在 OrderMilkTea 里面增加判断
 *  修改了原来的代码
 *
 *  改进思路：
 *     把创建 MilkTea 的类放入一个工厂类中，这样添加新的就只用改工厂类
 *
 *
 * @date created in 2021/1/13 16:36
 */
public class MilkTeaStore {
    public static void main(String[] args) {
//        new OrderMilkTea();
        new OrderMilkTea(new SimpleFactory());
    }
}
