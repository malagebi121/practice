package com.embrace.practice.designpattern.template;

/**
 * @author embrace
 * @describe  模板方法模式，父类抽象类在类中定义号算法和部奏，  公用的部分杂模板方法中定义
 * @date created in 2021/1/16 23:12
 */
public class client {
    public static void main(String[] args) {
        SoyaMilk soyaMilk = new BeanSoyaMilk();
        // 只有选料在子类中完成
        soyaMilk.make();
        System.out.println("--------------------");
        // 钩子方法，制作纯豆浆
        SoyaMilk soyaMilk2 = new PureSoyaMilk();
        soyaMilk2.make();
    }
}
