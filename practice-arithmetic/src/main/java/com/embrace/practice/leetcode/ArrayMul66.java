package com.embrace.practice.leetcode;

import java.util.Arrays;

/**
 * @author embrace
 * @describe
 *
 * 输入: [1,2,3,4,5]
 * 输出: [60,40,30,24]
 *  除开自己的乘积， 不能用除法
 *  1 3 4 5
 *  2 1 4 5
 *  2 3 1 5
 *  2 3 4 1
 * @date created in 2021/1/15 14:04
 */
public class ArrayMul66 {
    public static int[] constructArr(int[] a) {
        int[] res = new int[a.length];
        res[0] = 1;
        int tmp = 1;
        //左面三角形之和 [1,2,6,24]
        for (int i = 1; i < a.length; i++) {
            res[i] =  res[i - 1] * a[i-1];
        }
//        System.out.println(Arrays.asList(res));
        // 右面倒三角
        for(int i = a.length - 2; i >= 0; i --){
            tmp *= a[i + 1];
            res[i] *= tmp;
        }
//        System.out.println(res.toString());
        return res;
    }

    public static void main(String[] args) {
        constructArr(new int[]{1,2,3,4,5});
    }
}
