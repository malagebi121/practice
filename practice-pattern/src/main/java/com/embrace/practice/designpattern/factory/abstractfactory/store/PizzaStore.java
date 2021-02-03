package com.embrace.practice.designpattern.factory.abstractfactory.store;

import com.embrace.practice.designpattern.factory.abstractfactory.factory.BjFactory;
import com.embrace.practice.designpattern.factory.abstractfactory.order.OrderPizza;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/13 21:47
 */
public class PizzaStore {
    public static void main(String[] args) {
        new OrderPizza(new BjFactory());
    }
}
