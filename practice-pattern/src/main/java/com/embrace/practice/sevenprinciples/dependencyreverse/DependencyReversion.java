package com.embrace.practice.sevenprinciples.dependencyreverse;

/**
 * @author embrace
 * @describe  依赖倒转原则
 *
 *
 *
 * @date created in 2021/1/12 16:26
 */
public class DependencyReversion {
    public static void main(String[] args) {
        Person person = new Person();
        //获取信息，传入 Email
        person.receive(new Email());
    }
}

class Email{
    public void getInfo(){
        System.out.println("获取到邮件，hello kitty");
    }
}

/**
 *  这样很简单，但是如果接受的不是邮件，而是微信 qq 怎么办
 *  这就需要增加方法 和 微信类 等等
 *
 *  思路：
 *     用一个IReceive 接口提出 receive 方法 表示接受者， 这样 Person 依赖 IReceive 就好了
 *     让Email WeiXin 等分别实现receive 接口就好了，没必要传入改实体类， 具体实现也是在对应的类里面
 *
 */
class Person{
    public void receive(Email email){
        email.getInfo();
    }
}
