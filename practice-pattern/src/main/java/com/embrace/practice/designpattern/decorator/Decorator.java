package com.embrace.practice.designpattern.decorator;

/**
 * @author embrace
 * @describe  装饰者
 * @date created in 2021/1/14 23:10
 */
public class Decorator extends Drink {

    private Drink drink;

    public Decorator(Drink drink) {
        this.drink = drink;
    }

    @Override
    public float getCost() {
        return drink.getPrice() + super.getPrice();
    }

    @Override
    public String getDescribe() {
        return super.getDescribe() + "  " +super.getPrice()+ " && " + drink.getDescribe();
    }
}
