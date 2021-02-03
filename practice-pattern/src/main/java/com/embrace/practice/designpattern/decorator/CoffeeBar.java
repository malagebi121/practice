package com.embrace.practice.designpattern.decorator;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/14 23:22
 */
public class CoffeeBar {
    public static void main(String[] args) {
        // 点一份牛奶咖啡
        Drink drink = new MilkCoffee();
        System.out.println(drink.getCost());
        System.out.println(drink.getDescribe());
        //加豆浆
        Drink drink1 = new Soy(drink);
        System.out.println(drink1.getCost());
        System.out.println(drink1.getDescribe());

        // 加巧克力
        Drink drink2 = new Chocolate(drink1);
        System.out.println(drink2.getCost());
        System.out.println(drink2.getDescribe());
    }
}
