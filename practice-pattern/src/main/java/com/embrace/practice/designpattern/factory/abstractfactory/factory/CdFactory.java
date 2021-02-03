package com.embrace.practice.designpattern.factory.abstractfactory.factory;

import com.embrace.practice.designpattern.factory.abstractfactory.pizza.CdMilkPizza;
import com.embrace.practice.designpattern.factory.abstractfactory.pizza.CdPepperPizza;
import com.embrace.practice.designpattern.factory.abstractfactory.pizza.Pizza;
;


/**
 * @author embrace
 * @describe
 * @date created in 2021/1/13 21:41
 */
public class CdFactory implements AbstractFactory{

    @Override
    public Pizza createPizza(String orderType) {
        if(orderType.equals("cdMilk")){
            return  new CdMilkPizza();
        }else if(orderType.equals("cdPepper")){
            return new CdPepperPizza();
        }
        return  null;
    }
}
