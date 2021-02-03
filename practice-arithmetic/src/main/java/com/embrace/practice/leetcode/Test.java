package com.embrace.practice.leetcode;

import java.util.*;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/15 10:33
 */
public class Test {

    public static void main(String[] args) {
//        List<Integer> li = new ArrayList();
//        li.add(1);
//        List<Integer> l2 = li;
//
//        Integer[] a2 = (Integer[]) l2.toArray();
//        System.out.println(li.size());

//        for (int i = 10; i > 0; --i) {
//            System.out.println(i);
//        }


//        Integer[] a = new Integer[]{2,2,1,5,2,100,1,34,5,21};
//        Integer[] b = a.clone();
//        Arrays.sort(a, new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o2 -o1;  // 倒排
//            }
//        });
//        for (int i = 0; i < a.length; i++) {
//            System.out.println(a[i]);
//        }
//        for (int i = 0; i < b.length; i++) {
//            System.out.println(b[i]);
//        }
        //[[1,2,3],[4,5,6],[7,8,9]]
//        int[][] a = new int[3][3];
//        a[0] = new int[]{1,2,3};
//        a[1] = new int[]{4,5,6};
//        a[2] = new int[]{7,8,9};
//
//        System.out.println(a[0][0]  + " "  + a[1][0] + " " + a[2][0]);
        System.out.println(127>>>1);
        System.out.println(tableSizeFor(126));

        System.out.println(tableSizeFor2(16));

        new Thread( () -> {
            System.out.println("1231");
        }).start();

    }


    static  int tableSizeFor(int var0) {
        int var1 = var0 - 1;
        var1 |= var1 >>> 1;
        var1 |= var1 >>> 2;
        var1 |= var1 >>> 4;
        var1 |= var1 >>> 8;
        var1 |= var1 >>> 16;
        return var1 < 0 ? 1 : (var1 >= 1073741824 ? 1073741824 : var1 + 1);

    }

    static  int tableSizeFor2(int var0) {
        var0 = var0 - 1;
        int  i = 0;
        while(var0 > 0 ){
            i ++;
            var0 = var0 >>> 1;
        }
        return  i;
    }



}
