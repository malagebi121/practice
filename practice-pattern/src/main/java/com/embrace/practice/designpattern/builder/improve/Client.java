package com.embrace.practice.designpattern.builder.improve;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/18 19:12
 */
public class Client {
    public static void main(String[] args) {
        CommonHouse commonHouse = new CommonHouse();
        HouseDirector houseDirector = new HouseDirector(commonHouse);
        houseDirector.buildHouses();
    }
}
