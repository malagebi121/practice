package com.embrace.practice.designpattern.factory.simplefactory;

/**
 * @author embrace
 * @describe 简单工厂模式 ，  是的创建类在工厂类里面完成， 不用修改原来的业务
 *  新添加一种也很方便
 * @date created in 2021/1/13 17:38
 */
public class SimpleFactory {

    // 也可以用一般的
    public MilkTea CreateMilkTea(String orderType){
        MilkTea milkTea = null;
        if(orderType.equals("珍珠")){
            milkTea = new ZhenZhuMilkTea();
        }else if(orderType.equals("坚果")){
            milkTea = new JianGuoMilkTea();
        }
        return milkTea;
    }

    // 可以静态方法模式
    public static MilkTea CreateMilkTea2(String orderType){
        MilkTea milkTea = null;
        if(orderType.equals("珍珠")){
            milkTea = new ZhenZhuMilkTea();
        }else if(orderType.equals("坚果")){
            milkTea = new JianGuoMilkTea();
        }
        return milkTea;
    }
}
