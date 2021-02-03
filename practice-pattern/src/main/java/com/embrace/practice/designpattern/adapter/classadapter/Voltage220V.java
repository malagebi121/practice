package com.embrace.practice.designpattern.adapter.classadapter;

/**
 * @author embrace
 * @describe  目标类
 * @date created in 2021/1/14 20:51
 */
public class Voltage220V {
    public int getVoltage220V(){
        System.out.println("获取电源220V");
        return 220;
    }
}
