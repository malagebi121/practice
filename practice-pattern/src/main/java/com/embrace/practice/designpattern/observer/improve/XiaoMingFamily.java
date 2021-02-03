package com.embrace.practice.designpattern.observer.improve;

import org.omg.CORBA.Object;

/**
 * @author embrace
 * @describe   小明家
 *  实现Observer
 * @date created in 2021/1/14 11:24
 */
public class XiaoMingFamily implements Observer {
    //属性名字
    private  String familyName;
    public XiaoMingFamily(String familyName){
        this.familyName = familyName;
    }
    @Override
    public boolean notice(String msg) {
        System.out.println(familyName + "接收到消息： " + msg);
        return true;
    }
    @Override
    public String getName() {
        return familyName;
    }
}
