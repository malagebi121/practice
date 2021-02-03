package com.embrace.practice.leetcode;

/**
 * @author embrace
 * @describe
 *
 * 排序数组中的长度， 不开辟新空间 ，不用 set
 * 用快慢的数组指针方法
 *
 * @date created in 2021/1/15 15:25
 */
public class ArraySortReallyLength26 {
    public int removeDuplicates(int[] nums) {
        int j = 0;
        for(int i = 1; i < nums.length; i++){
            if(nums[i] != nums[j])
                j++;
            nums[j] = nums[i];
        }
        return  j + 1;
    }
}
