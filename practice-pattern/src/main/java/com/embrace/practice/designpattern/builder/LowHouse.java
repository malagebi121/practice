package com.embrace.practice.designpattern.builder;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/18 18:22
 */
public class LowHouse extends AbstractHouse{
    @Override
    public void buildBasic() {
        System.out.println("低矮盖房子，打地基");

    }

    @Override
    public void buildWalls() {
        System.out.println("低矮房子建造墙");

    }

    @Override
    public void ruffed() {
        System.out.println("低矮房子封顶了");
    }
}
