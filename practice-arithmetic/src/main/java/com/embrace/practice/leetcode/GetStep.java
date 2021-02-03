package com.embrace.practice.leetcode;

/**
 * @author embrace
 * @describe  跳楼梯， 一步， 两步， 三步
 *
 *
 * @date created in 2021/1/15 16:31
 */
public class GetStep {

    //O1 次 不开闭空间的方法  a  b  每次向前进一步
    public int numWays(int n) {
        if(n == 0 || n == 1){
            return 1;
        }
        if(n == 2){
            return 2;
        }
        int a = 1;
        int b = 2;
        for(int i = 0;i < n-2; i++){
            int b_temp = b;
            b = (b + a ) % 1000000007;
            a = b_temp;
        }
        return b;
    }
}
