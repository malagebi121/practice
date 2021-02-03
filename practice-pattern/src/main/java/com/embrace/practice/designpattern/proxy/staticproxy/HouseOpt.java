package com.embrace.practice.designpattern.proxy.staticproxy;

/**
 * @author embrace
 * @describe   优点： 开始实现代理
 *
 * 缺点： 代理与目标对象都要实现共同的接口
 *       不利于扩展
 * @date created in 2021/1/14 14:35
 */
public class HouseOpt {
    public static void main(String[] args) {
        // 目标对象
        HouseOwner houseOwner = new HouseOwner();
        //代理对象
        RentHouse rentHouse = new HouseProxy(houseOwner);
        //代理对象去做
        rentHouse.rent();
    }
}
