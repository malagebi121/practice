package com.embrace.practice.reference;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * @author embrace
 * @describe
 * @date created in 2020/12/22 21:45
 */
public class WeakHashMapDemo {
    public static void main(String[] args) {
        weakHash();
    }

    public static void weakHash(){
        System.out.println("==========  HashMap   ===========");
        HashMap<Integer, String> map = new HashMap<>();
        Integer integer = new Integer(1);
        String value = "HashMap";
        map.put(integer, value);
        integer = null;
        System.out.println(map); //{1=HashMap}
        System.gc();
        System.out.println(map);//{1=HashMap}

        System.out.println("==========  WeakHashMap   ===========");
        WeakHashMap<Integer, String> weakHashMap = new WeakHashMap<>();
        Integer integer2 = new Integer(1);
        String value2 = "WeakHashMap";
        weakHashMap.put(integer2, value);
        integer2 = null;
        System.out.println(weakHashMap); //{1=HashMap}
        System.gc(); //gc 弱引用都要被回收
        System.out.println(weakHashMap  + "\t" + weakHashMap.size());//{}	0


    }
}
