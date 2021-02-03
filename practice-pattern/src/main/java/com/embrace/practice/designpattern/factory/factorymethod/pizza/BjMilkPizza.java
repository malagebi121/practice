package com.embrace.practice.designpattern.factory.factorymethod.pizza;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/13 20:45
 */
public class BjMilkPizza extends Pizza {
    @Override
    public void prepare() {
        setName("北京奶味pizza");
        System.out.println("北京奶味pizza 准备材料");
    }
}
