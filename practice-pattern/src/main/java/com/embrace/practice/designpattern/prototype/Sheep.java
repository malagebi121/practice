package com.embrace.practice.designpattern.prototype;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/13 22:17
 */
public class Sheep implements Cloneable{
    private String name;
    private int age;
    private String color;
    public Sheep sheep;

    public Sheep(String name, int age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Sheep{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
