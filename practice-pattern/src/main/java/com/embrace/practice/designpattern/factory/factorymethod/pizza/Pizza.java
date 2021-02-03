package com.embrace.practice.designpattern.factory.factorymethod.pizza;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/13 20:42
 */
public abstract class Pizza {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public abstract void prepare();

    public void step1(){
        System.out.println(name + "初始化第一步");
    }

    public void step2(){
        System.out.println(name + "初始化第二步，然后包装完毕");
    }
}
