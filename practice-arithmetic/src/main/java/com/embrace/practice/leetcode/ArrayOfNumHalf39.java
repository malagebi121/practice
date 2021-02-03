package com.embrace.practice.leetcode;

/**
 * @author embrace
 * @describe  数组中超过一半的数据
 *
 * 采用投票法则，不一样的减一，
 * 一样的票数加一
 *
 * 票数为零时，第一个出现的数字放入， 票数加一
 *
 *
 * @date created in 2021/1/15 14:57
 */
public class ArrayOfNumHalf39 {
    public int majorityElement(int[] nums) {
        //数字
        int num = 0 ;
        //数字出现的次数
        int votes = 0;
        for(int i = 0; i < nums.length; i++){
            if(votes == 0) num = nums[i];
            votes += num == nums[i] ? 1: -1;
        }
        return num;

    }
}
