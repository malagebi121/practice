package com.embrace.practice.designpattern.adapter.classadapter;

/**
 * @author embrace
 * @describe  适配器类
 * @date created in 2021/1/14 20:54
 */
public class Voltage10VAdapter extends Voltage220V implements IVoltage10V {
    @Override
    public int getVoltage10V() {
        int  voltage = getVoltage220V();
        int result = voltage / 22;
        System.out.println("适配器输出10V");
        return result;
    }
}
