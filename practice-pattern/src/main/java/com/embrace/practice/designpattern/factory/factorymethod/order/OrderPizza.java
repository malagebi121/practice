package com.embrace.practice.designpattern.factory.factorymethod.order;

import com.embrace.practice.designpattern.factory.factorymethod.pizza.Pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author embrace
 * @describe  抽象 pizza 订购类
 * @date created in 2021/1/13 20:50
 */
public abstract class OrderPizza {

    // 定义一个抽象方法，让工工厂子类自己实现
    abstract Pizza createPizza(String OrderType);

    public  OrderPizza(){
        Pizza pizza = null;
        String orderType = "";
        do {
            orderType = getType();
            pizza = createPizza(orderType);
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
