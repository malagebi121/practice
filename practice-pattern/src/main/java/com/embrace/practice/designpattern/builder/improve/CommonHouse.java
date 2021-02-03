package com.embrace.practice.designpattern.builder.improve;

/**
 * @author embrace
 * @describe   指挥者，这里去指定流程
 * @date created in 2021/1/18 18:58
 */
public class CommonHouse extends HouseBuilder {
    @Override
    public void buildBasic() {
        house.setBasic("地基种类");
        System.out.println("普通房子打地基");
    }

    @Override
    public void buildWalls() {
        System.out.println("普通房子建墙");
    }

    @Override
    public void roofed() {
        System.out.println("普通房子封顶");
    }
}
