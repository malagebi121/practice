package com.embrace.practice.designpattern.adapter.interfaceadapter;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/14 21:20
 */
public class Client {
    public static void main(String[] args) {
        AbstractInterface abstractInterface = new AbstractInterface(){
            // 里面只需要去覆盖我们需要的方法就可以了
            @Override
            public void method1() {
                System.out.println("我只实现了method1");
            }
        };

        abstractInterface.method1();
    }
}
