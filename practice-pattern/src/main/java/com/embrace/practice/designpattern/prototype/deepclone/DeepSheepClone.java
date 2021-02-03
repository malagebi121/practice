package com.embrace.practice.designpattern.prototype.deepclone;

import com.embrace.practice.designpattern.prototype.Sheep;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/13 22:47
 */
public class DeepSheepClone {
    public static void main(String[] args) throws CloneNotSupportedException {
        DeepSheep sheep = new DeepSheep("程序羊",2,"白色");
        //这个属性只是复制了地址
        sheep.sheep = new DeepSheep("程序羊",3,"黑色");
//        System.out.println(sheep.sheep);
//        DeepSheep sheep1 = (DeepSheep) sheep.clone();
//        DeepSheep sheep2 = (DeepSheep) sheep.clone();
//        DeepSheep sheep3 = (DeepSheep) sheep.clone();
//
//        System.out.println(sheep1);
//        System.out.println(sheep2);
//        System.out.println(sheep3);
//
//        System.out.println(sheep1.sheep.toString());
//        System.out.println(sheep1.sheep == sheep2.sheep);


        //方式二深克隆，用流的方式重新造了对象
        DeepSheep sheep1 = (DeepSheep) sheep.deepClone();
        DeepSheep sheep2 = (DeepSheep) sheep.deepClone();

        System.out.println(sheep1);
        System.out.println(sheep2);

        System.out.println(sheep2.sheep == sheep1.sheep);
    }
}
