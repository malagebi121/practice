package com.embrace.practice.designpattern.factory.abstractfactory.factory;

import com.embrace.practice.designpattern.factory.abstractfactory.pizza.BjMilkPizza;
import com.embrace.practice.designpattern.factory.abstractfactory.pizza.BjPepperPizza;
import com.embrace.practice.designpattern.factory.abstractfactory.pizza.Pizza;


/**
 * @author embrace
 * @describe
 * @date created in 2021/1/13 21:40
 */
public class BjFactory implements  AbstractFactory {
    @Override
    public Pizza createPizza(String orderType) {
        System.out.println(orderType);
        if(orderType.equals("bjMilk")){
            return  new BjMilkPizza();
        }else if(orderType.equals("bjPepper")){
            return new BjPepperPizza();
        }
        return  null;
    }
}
