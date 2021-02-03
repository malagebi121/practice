package com.embrace.practice.designpattern.prototype;

/**
 * @author embrace
 * @describe  引用对象浅拷贝
 * @date created in 2021/1/13 22:20
 */
public class SheepClone {
    public static void main(String[] args) throws CloneNotSupportedException {
        Sheep sheep = new Sheep("程序羊",2,"白色");
        //这个属性只是复制了地址
        sheep.sheep = new Sheep("程序羊",3,"黑色");
        Sheep sheep1 = (Sheep) sheep.clone();
        Sheep sheep2 = (Sheep) sheep.clone();
        Sheep sheep3 = (Sheep) sheep.clone();
        Sheep sheep4 = (Sheep) sheep.clone();

        System.out.println(sheep1);
        System.out.println(sheep2);
        System.out.println(sheep3);

        System.out.println(sheep1.sheep.toString());
        System.out.println(sheep1.sheep == sheep2.sheep);
    }
}
