package com.embrace.practice.designpattern.adapter.objectadapter;

/**
 * @author embrace
 * @describe 适配器类
 * @date created in 2021/1/14 20:54
 */
public class Voltage10VAdapter implements IVoltage10V {

    // 聚合关系，uml 实现箭头 Voltage220V 指向 Voltage10VAdapter
    private  Voltage220V voltage220V;

    public Voltage10VAdapter(Voltage220V voltage220V) {
        this.voltage220V = voltage220V;
    }

    @Override
    public int getVoltage10V() {
        int  voltage = voltage220V.getVoltage220V();
        int result = voltage / 22;
        System.out.println("适配器输出10V");
        return result;
    }
}
