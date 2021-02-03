package com.embrace.practice.designpattern.prototype.deepclone;

import java.io.*;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/13 22:44
 */
public class DeepSheep  implements Serializable,Cloneable{
    private String name;
    private int age;
    private String color;
//    public DeepSheep sheep = new DeepSheep("程序羊",3,"黑色");  //这样直接 java.lang.StackOverflowError
    public DeepSheep sheep;

    public DeepSheep(String name, int age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
//        sheep = new DeepSheep("程序羊",3,"黑色");  //这样直接 java.lang.StackOverflowError  ，因为会无限循环下去
    }

    @Override
    public String toString() {
        return "DeepSheep{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                '}';
    }

    // 实现深拷贝方式一
    //这里如果是相同的对象，会一直调用下去，所以要判断 是否为null
    @Override
    protected Object clone() throws CloneNotSupportedException {
        //这里怎么报错了
        DeepSheep deepSheep = (DeepSheep) super.clone();
        DeepSheep deepSheep1 = deepSheep.sheep;
        if(deepSheep1 != null){
            deepSheep.sheep = (DeepSheep) deepSheep1.clone();
        }
        return deepSheep;
    }

    // 实现深拷贝方式二
    public DeepSheep deepClone(){
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;

        try {
            //序列化
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            //反序列化
            bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            DeepSheep deepSheep = (DeepSheep) ois.readObject();

            return deepSheep;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            //close
            try {
                bos.close();
                oos.close();
                bis.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
