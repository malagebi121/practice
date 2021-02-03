package com.embrace.practice.designpattern.factory.factorymethod.order;

import com.embrace.practice.designpattern.factory.factorymethod.pizza.*;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/13 20:54
 */
public class CdOrderPizza extends OrderPizza {
    @Override
    Pizza createPizza(String OrderType) {
        if(OrderType.equals("cdMilk")){
            return  new CdMilkPizza();
        }else if(OrderType.equals("cdPepper")){
            return new CdPepperPizza();
        }
        return  null;
    }
}
