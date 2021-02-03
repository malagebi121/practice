package com.embrace.practice.designpattern.adapter.classadapter;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/14 20:55
 */
public class ClassAdapterTest {
    public static void main(String[] args) {
        IVoltage10V voltage10V = new Voltage10VAdapter();
        System.out.println("使用适配器输出 " + voltage10V.getVoltage10V() + " V");
    }
}
