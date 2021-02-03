package com.embrace.practice.sevenprinciples.dependencyreverse;

/**
 * @author embrace
 * @describe  依赖倒转更改
 *    提出 IReceive 接口，让WeiXin  QQ  Email 实现
 *    Person 只需要依赖 IReceive 接口 ，传入不同的  IReceive 实现即可以实现不同的消息接收
 * @date created in 2021/1/12 16:44
 */
public class DependencyReversionModify {
    public static void main(String[] args) {
        Person1 person1 = new Person1();

        person1.receive(new QQ());
        person1.receive(new WeiXin());
    }
}


class Person1{
    void receive(IReceive receive){
        receive.receive();
    }
}

interface  IReceive{
    void receive();
}

class WeiXin implements  IReceive{

    @Override
    public void receive() {
        System.out.println("微信消息来了");
    }
}


class QQ implements IReceive{

    @Override
    public void receive() {
        System.out.println("QQ 消息来了");
    }
}