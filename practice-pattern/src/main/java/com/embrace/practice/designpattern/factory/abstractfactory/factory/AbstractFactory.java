package com.embrace.practice.designpattern.factory.abstractfactory.factory;


import com.embrace.practice.designpattern.factory.abstractfactory.pizza.Pizza;

/**
 * @author embrace
 * @describe
 *   工厂抽象层 , 对不同工厂的工厂进行管理
 * @date created in 2021/1/13 21:35
 */
public interface  AbstractFactory {
     Pizza createPizza(String orderType);
}
