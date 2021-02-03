package com.embrace.practice.designpattern.factory.abstractfactory.pizza;

/**
 * @author embrace
 * @describe 成都椒盐pizza
 * @date created in 2021/1/13 20:48
 */
public class CdPepperPizza extends Pizza {
    @Override
    public void prepare() {
        setName("成都椒盐pizza");
        System.out.println("成都椒盐pizza ，准被材料");
    }
}
