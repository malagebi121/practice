package com.embrace.practice.designpattern.proxy.staticproxy;

/**
 * @author embrace
 * @describe 房租中介，代理类
 * @date created in 2021/1/14 14:18
 */
public class HouseProxy implements RentHouse {
    private RentHouse rentHouse;
    public HouseProxy(RentHouse rentHouse) {
        this.rentHouse = rentHouse;
    }
    @Override
    public void rent() {
        //看房子
        seeHouse();
        //真的租房子
        rentHouse.rent();
        //收费
        getMoney();
    }
    private void seeHouse() {
        System.out.println("看房子");
    }
    private void getMoney() {
        System.out.println("租房子");
    }
}
