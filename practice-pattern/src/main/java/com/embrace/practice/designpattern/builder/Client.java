package com.embrace.practice.designpattern.builder;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/18 18:24
 */
public class Client {
    public static void main(String[] args) {
        AbstractHouse house = new HighHouse();
        house.build();
    }
}
