package com.embrace.practice.designpattern.factory.simplefactory;

/**
 * @author embrace
 * @describe  珍珠奶茶
 * @date created in 2021/1/13 16:42
 */
public class ZhenZhuMilkTea extends MilkTea{

    public ZhenZhuMilkTea(){
        this.name = "珍珠奶茶";
    }

    @Override
    public void prepare() {
        System.out.println("珍珠奶茶准备 珍珠 牛奶 原料 ");
    }
}
