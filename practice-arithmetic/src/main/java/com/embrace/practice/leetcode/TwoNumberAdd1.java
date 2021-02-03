package com.embrace.practice.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author embrace
 * @describe   找出数组中两树之和的值 等于 某个数
 *
 *  暴力解法O(n^2)   hash  的话就是 O(n)
 *
 *    [2,3,5,21,3,1,5]  等于 24   返回  [1,3]  位置
 *   最优解： 定义 hash  ,  存入 key 为 value 值为  i 的值，
 *   没有的话把这次的值加入hash，不管是否有重复
 *
 * @date created in 2021/1/15 11:14
 */
public class TwoNumberAdd1 {

    public static void main(String[] args) {
        Integer[] array = new Integer[]{2,3,5,21,3,1,5};
        Integer[] result = null;
        int  num = 24;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            int n = num - array[i];
            if(map.containsKey(n)){
                System.out.println(map.get(n) + "   " + i);
                //结果
                result = new Integer[]{map.get(n),i};
                return;
            }
            map.put(array[i],i);
        }
    }

}
