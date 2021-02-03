package com.embrace.practice.designpattern.builder.improve;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/18 18:55
 */
public abstract class HouseBuilder {

    public House house = new House();

    // 打地基
    public abstract void buildBasic();
    //砌墙
    public abstract void buildWalls();
    //f封顶
    public abstract void roofed();

    //返回House
    public House build(){
       return house;
    }




}
