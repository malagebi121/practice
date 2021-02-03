package com.embrace.practice.designpattern.factory.factorymethod.order;

import com.embrace.practice.designpattern.factory.factorymethod.pizza.BjMilkPizza;
import com.embrace.practice.designpattern.factory.factorymethod.pizza.BjPepperPizza;
import com.embrace.practice.designpattern.factory.factorymethod.pizza.Pizza;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/13 20:54
 */
public class BjOrderPizza extends OrderPizza {
    @Override
    Pizza createPizza(String orderType) {
        System.out.println(orderType);
        if(orderType.equals("bjMilk")){
            return  new BjMilkPizza();
        }else if(orderType.equals("bjPepper")){
            return new BjPepperPizza();
        }
        return  null;
    }
}
