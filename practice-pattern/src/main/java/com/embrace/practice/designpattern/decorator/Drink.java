package com.embrace.practice.designpattern.decorator;

/**
 * @author embrace
 * @describe  装饰者模式
 *   例子： 不同的 coffee  加不同的调料   得到价格和 描述
 *
 *
 *
 *
 *
 * @date created in 2021/1/14 22:44
 */
public abstract class Drink {

    private float price;

    private String describe;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public abstract float getCost();
}
