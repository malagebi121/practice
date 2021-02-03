package com.embrace.practice.designpattern.builder.improve;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/18 18:53
 */
public class House {
    private String basic;
    private String walls;
    private String roofed;

    public String getBasic() {
        return basic;
    }

    public void setBasic(String basic) {
        this.basic = basic;
    }

    public String getWalls() {
        return walls;
    }

    public void setWalls(String walls) {
        this.walls = walls;
    }

    public String getRoofed() {
        return roofed;
    }

    public void setRoofed(String roofed) {
        this.roofed = roofed;
    }
}
