package com.embrace.practice.sevenprinciples.Interfaceisolation.improve;

/**
 * @author embrace
 * @describe  接口隔离原则（拆分接口）
 *
 *
 *
 *
 *  A 只需要 C  run123方法
 *  B 只需要 D  run134方法
 *
 *  根据接口隔离可以将 Interface1 拆分
 *  拆分为 run13  一个接口，run2 和 run4 两个接口
 *
 *  C 实现 run13 和 run2 接口就好了
 *  D 实现 run13 和 run4 接口接好了
 * @date created in 2021/1/12 14:27
 */
public class InterfaceIsolation {
    public static void main(String[] args) {
        A a = new A();
        Interface1 interface1ForA = new C();
        a.run1(interface1ForA);
        a.run2(interface1ForA);
        a.run3(interface1ForA);

        B b = new B();
        Interface1 interface1ForB= new D();
        b.run1(interface1ForB);
        b.run3(interface1ForB);
        b.run4(interface1ForB);

    }
}

class A {
    public void run1(Interface1 interface1){
        interface1.run1();
    }

    public void run2(Interface1 interface1){
        interface1.run2();
    }
    public void run3(Interface1 interface1){
        interface1.run3();
    }
}

class B {
    public void run1(Interface1 interface1){
        interface1.run1();
    }

    public void run3(Interface1 interface1){
        interface1.run3();
    }
    public void run4(Interface1 interface1){
        interface1.run4();
    }
}

/**
 *  A 只需要 C  run123方法
 *  B 只需要 D  run134方法
 *
 *  根据接口隔离可以将 Interface1 拆分
 *  拆分为 run13  一个接口，run2 和 run4 两个接口
 *
 *  C 实现 run13 和 run2 接口就好了
 *  D 实现 run13 和 run4 接口接好了
 */
interface Interface1{
    void run1();
    void run2();
    void run3();
    void run4();
}


class C implements Interface1{
    @Override
    public void run1() {
        System.out.println(this.getClass().getName() + "实现的方法 run1");
    }

    @Override
    public void run2() {
        System.out.println(this.getClass().getName() +" C 实现的方法 run2");
    }

    @Override
    public void run3() {
        System.out.println(this.getClass().getName() +" C 实现的方法 run3");
    }

    @Override
    public void run4() {
        System.out.println(this.getClass().getName() +" C 实现的方法 run4");
    }
}

class D implements Interface1{
    @Override
    public void run1() {
        System.out.println(this.getClass().getName() + "实现的方法 run1");
    }

    @Override
    public void run2() {
        System.out.println(this.getClass().getName() +" C 实现的方法 run2");
    }

    @Override
    public void run3() {
        System.out.println(this.getClass().getName() +" C 实现的方法 run3");
    }

    @Override
    public void run4() {
        System.out.println(this.getClass().getName() +" C 实现的方法 run4");
    }
}
