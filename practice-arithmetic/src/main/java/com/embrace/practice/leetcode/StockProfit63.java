package com.embrace.practice.leetcode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author embrace
 * @describe
 * 股票的最大利润
 *
 *
 * 变式：
 * 返回买入卖出的时间，以及最大利润
 * 做波段的最大利润？，并列出波段
 *  完成K笔交易的最大利润 ，分列出波段
 * @date created in 2021/1/15 15:30
 */
public class StockProfit63 {
    public int maxProfit(int[] prices) {
        // 设置最小的值适合抄底
        int min = Integer.MAX_VALUE;
        //最大利润
        int max = 0;
        for (int i = 0; i < prices.length; i++) {
            min = Math.min(min , prices[i]);
            //每天的利润 prices[i] - min  ，对比以往每天的利润
            max = Math.max(max, prices[i] - min);
        }
        return max;
    }


    // 输出最大利润以及出现的时间
    public int[] maxProfits(int[] prices) {
        // 设置最小的值适合抄底
        int min = Integer.MAX_VALUE;
        // 出现时间
        int minDay = 0;
        int maxDay = 0;
        //最大利润
        int max = 0;
        for (int i = 0; i < prices.length; i++) {

            min = Math.min(min , prices[i]);
            //每天的利润 prices[i] - min  ，对比以往每天的利润
            max = Math.max(max, prices[i] - min);
        }
        return new int[2] ;
    }


    // 输出最大利润以及出现的时间
    public int[] maxP(int[] prices) {
        List<StockLine> list = new ArrayList<>();
        for (int i = 0; i < prices.length; i++) {
            list.add(new StockLine());
        }

        // 设置最小的值适合抄底
        int min = Integer.MAX_VALUE;
        // 出现时间
        int minDay = 0;
        int maxDay = 0;
        //最大利润
        int max = 0;
        for (int i = 0; i < prices.length; i++) {

            min = Math.min(min , prices[i]);
            //每天的利润 prices[i] - min  ，对比以往每天的利润
            max = Math.max(max, prices[i] - min);
        }
        return new int[2] ;
    }

    // 股票线路对象
    class StockLine{
        int day;
        int money;
        int minDay;
        int maxProfit;
    }

}

