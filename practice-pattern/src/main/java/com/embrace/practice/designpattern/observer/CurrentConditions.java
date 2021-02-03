package com.embrace.practice.designpattern.observer;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/14 10:45
 */
public class CurrentConditions {
    // 温度
    private float temperature;
    // 气压
    private float pressure;
    // 湿度
    private float humidity;

    public  void update(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        display();
    }

    public void display(){
        System.out.println("天气变化");
        System.out.println("温度" + temperature + " 度");
        System.out.println("气压" + pressure + " 个");
        System.out.println("湿度" + temperature);
    }
}
