package com.embrace.practice.designpattern.factory.simplefactory;

/**
 * @author embrace
 * @describe  抽象出来的
 * @date created in 2021/1/13 16:37
 */
public abstract  class MilkTea {

    protected String name;

    // 不同的奶茶原料不同，自己实现
    public abstract void  prepare();

    // 加水
    public void addWater(){
        System.out.println(name + "  正在加水");
    }

    // 混合
    public void mix(){
        System.out.println(name + "  正在混合");
    }

    // 打包
    public void pack(){
        System.out.println(name + "  正在包装");
    }

    //设置名字
    public void setName(String name) {
        this.name = name;
    }
}
