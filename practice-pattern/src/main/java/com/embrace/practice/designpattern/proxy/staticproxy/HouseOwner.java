package com.embrace.practice.designpattern.proxy.staticproxy;

/**
 * @author embrace
 * @describe  房东 目标对象，要租房子
 * @date created in 2021/1/14 14:17
 */
public class HouseOwner implements RentHouse {
    @Override
    public void rent() {
        System.out.println("房东租房子");
    }
}
