package com.embrace.practice.designpattern.factory.abstractfactory.pizza;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/13 20:47
 */
public class CdMilkPizza extends Pizza {
    @Override
    public void prepare() {
        setName("成都奶味pizza");
        System.out.println("成都奶味 pizza ， 准备材料");
    }
}
