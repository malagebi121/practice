package com.embrace.practice.designpattern.builder;

/**
 * @author embrace
 * @describe   建造者的抽象类, 产品和产品建造过程依赖太高，需要解耦
 * @date created in 2021/1/18 18:17
 */
public abstract class AbstractHouse {
    // 打地基
    public abstract void buildBasic();
    //砌墙
    public abstract void buildWalls();
    //f封顶
    public abstract void ruffed();

    public void build(){
        buildBasic();
        buildWalls();
        ruffed();
    }
}
