package com.embrace.practice.designpattern.factory.factorymethod.pizza;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/13 20:46
 */
public class BjPepperPizza extends Pizza {
    @Override
    public void prepare() {
        setName("北京椒盐pizza");
        System.out.println("北京椒盐pizza 准备材料");
    }
}
