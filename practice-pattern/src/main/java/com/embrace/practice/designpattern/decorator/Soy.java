package com.embrace.practice.designpattern.decorator;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/14 23:21
 */
public class Soy extends Decorator {
    public Soy(Drink drink) {
        super(drink);
        setPrice(1.5f);
        setDescribe("豆浆");
    }
}
