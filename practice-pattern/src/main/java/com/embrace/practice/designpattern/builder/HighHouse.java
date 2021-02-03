package com.embrace.practice.designpattern.builder;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/18 18:21
 */
public class HighHouse extends AbstractHouse{

    @Override
    public void buildBasic() {
        System.out.println("高楼盖房子，打地基");
    }

    @Override
    public void buildWalls() {
        System.out.println("高楼建造墙");
    }

    @Override
    public void ruffed() {
        System.out.println("高楼封顶了");
    }
}
