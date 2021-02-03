package com.embrace.practice.leetcode;

/**
 * @author embrace
 * @describe
 *
 *  打印二纬数组
 *
 * @date created in 2021/1/15 17:12
 */
public class PrintJuXing29 {
    public int[] spiralOrder(int[][] matrix) {
        if(matrix.length == 0) return new int[0];
        int[] a = new int[matrix.length * matrix[0].length];
        int columns  = matrix[0].length -1;
        int rows = matrix.length - 1;
        int left = 0;int right = columns; int top = 0;int bottom = rows;
        int k = 0;
        while (true){
            //从左到右
            for (int i = left; i <= right; i++) {
                a[k++] = matrix[top][i];
            } top ++;if(top > bottom) break;

            //从上往下
            for (int i = top; i <= bottom; i++) {
                a[k++] = matrix[i][right];
            } right --;if(right < left) break;

            //从右到左往
            for (int i = right; i >= left; i--) {
                a[k++] = matrix[bottom][i];
            } bottom --;if(bottom < top) break;

            //从下往上
            for (int i = bottom; i >= top; i--) {
                a[k++] = matrix[i][left];
            } left ++;if(left > right) break;
        }
        return a;
    }

    // 别人写的
    public int[] spiralOrder2(int[][] matrix) {
        if(matrix.length == 0) return new int[0];
        int l = 0, r = matrix[0].length - 1, t = 0, b = matrix.length - 1, x = 0;
        int[] res = new int[(r + 1) * (b + 1)];
        while(true) {
            for(int i = l; i <= r; i++) res[x++] = matrix[t][i]; // left to right.
            if(++t > b) break;
            for(int i = t; i <= b; i++) res[x++] = matrix[i][r]; // top to bottom.
            if(l > --r) break;
            for(int i = r; i >= l; i--) res[x++] = matrix[b][i]; // right to left.
            if(t > --b) break;
            for(int i = b; i >= t; i--) res[x++] = matrix[i][l]; // bottom to top.
            if(++l > r) break;
        }
        return res;
    }


}
