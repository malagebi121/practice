package com.embrace.practice.designpattern.observer;

/**
 * @author embrace
 * @describe
 *
 * 普通方法，将要通知的类注入天气类
 * 天气类一旦触发修改天气后调用 注入类的修改，修改里面调用显示或者推送等
 *
 *
 *缺点： 要新接入一个通知类改动太大了
 *
 *
 * @date created in 2021/1/14 10:57
 */
public class DisplayWeather {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        weatherData.setCurrentConditions(new CurrentConditions());
        weatherData.setData(10f,101f,11f);
    }
}
