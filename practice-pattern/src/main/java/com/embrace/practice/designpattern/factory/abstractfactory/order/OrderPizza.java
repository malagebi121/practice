package com.embrace.practice.designpattern.factory.abstractfactory.order;

import com.embrace.practice.designpattern.factory.abstractfactory.factory.AbstractFactory;
import com.embrace.practice.designpattern.factory.abstractfactory.pizza.Pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/13 21:43
 */
public class OrderPizza {

    private AbstractFactory factory;



    public OrderPizza(AbstractFactory factory){
        setFactory(factory);
    }

    private void setFactory(AbstractFactory factory){
        Pizza pizza = null;
        String orderType = "";
        do {
            orderType = getType();
            pizza = factory.createPizza(orderType);
            doPizza(pizza);
        }while (true);
    }

    private String getType(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("输入想要的pizza");
        String s = "";
        try {
            s = bufferedReader.readLine();
//            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public void  doPizza(Pizza pizza){
        if(pizza != null){
            pizza.prepare();
            pizza.step1();
            pizza.step2();
        }
    }


}
