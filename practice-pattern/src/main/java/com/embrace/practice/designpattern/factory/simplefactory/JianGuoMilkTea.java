package com.embrace.practice.designpattern.factory.simplefactory;

/**
 * @author embrace
 * @describe  坚果奶茶
 * @date created in 2021/1/13 16:46
 */
public class JianGuoMilkTea extends MilkTea {

    public JianGuoMilkTea(){
        this.name = "坚果奶茶";
    }

    @Override
    public void prepare() {
        System.out.println("准备坚果奶茶  坚果  奶茶");
    }
}
