package com.embrace.practice.leetcode;

/**
 * @author embrace
 * @describe
 *
 *统计一个数字在排序数组中出现的次数。
 * 用二分法
 *
 *
 * @date created in 2021/1/15 16:52
 */
public class SortArrayNumber53 {
    public int search(int[] nums, int target) {
        int left =0,right = nums.length-1;
        int count = 0;
        while(left<right){
            int mid = (left+right)/2;
            if(nums[mid]>=target)
                right=mid;
            if(nums[mid]<target)
                // 这个小于一定要加一
                left = mid+1;
        }
        while(left<nums.length && nums[left++] == target)
            count++;
        return count;
    }
}
