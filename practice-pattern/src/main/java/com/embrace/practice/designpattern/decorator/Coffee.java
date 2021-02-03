package com.embrace.practice.designpattern.decorator;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/14 22:46
 */
public class Coffee extends Drink{
    @Override
    public float getCost() {
        return super.getPrice();
    }
}
