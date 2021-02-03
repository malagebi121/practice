package com.embrace.practice.designpattern.decorator;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/14 23:19
 */
public class Chocolate extends Decorator {
    public Chocolate(Drink drink) {
        super(drink);
        setDescribe("巧克力");
        setPrice(1.0f);
    }
}
