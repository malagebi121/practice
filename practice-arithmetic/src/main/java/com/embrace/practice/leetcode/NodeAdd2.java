package com.embrace.practice.leetcode;

/**
 *   leetCode 题目2
 *
 *   最主要的是定义虚拟头节点  ，
 *   让会移动的节点指向虚拟头节点， 返回 虚拟节点的  next
 *
 *   题目定义：   单向链表相加   1 > 2  >  3   =  321  , 9 > 1  = 19
 *   321  + 19 = 340  链表  043
 *
 *   题解：
 *       遍历链表    123  +  910
 *       当两个 node 都不是 null  继续相加， 外部定义进位add    值为0或者1
 *       sum = node1.val + node2.val + add
 *       相加的 sum  ，sum / 10 等于进位， sum % 10 等于下一位
 *
 *
 *  题目变式：用数组装载数据
 *      [1,2,3]  [9,1]
 *     思路： 得到最大数组长度遍历
 *     小于最大长度的为零， 用 list 装载数据，最后转为 array
 *
 */
class NodeAdd2 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //定义
        ListNode pre = new ListNode(0);
        ListNode cur = pre;
        //进位数，不是1就是0
        int add = 0;
        while(l1 != null || l2 !=null){
            int a = l1 == null ? 0 : l1.val;
            int b = l2 == null ? 0 : l2.val;
            int sum = a + b + add;
            //进位数
            add = sum / 10;
            //余数就是本位数
            sum = sum % 10;
            ListNode node = new ListNode(sum);
            cur.next = node;
            cur = cur.next;

            if(l1 != null){
                l1 = l1.next;
            }
            if(l2 != null){
                l2 = l2.next;
            }
        }
        if(add == 1) {
            cur.next = new ListNode(add);
        }
        return pre.next;
    }

    public static void main(String[] args) {
        ListNode pre = new ListNode(0);
        ListNode cur =  pre;
        for (int i = 1; i < 10; i++) {
            //在这一步 cur 还是指向
            cur.next = new ListNode(i); //  next 指向了新地址，相当于 pre对象 的 next 已经是新地址了
            //这移动的 cur 的指向已经变了
            cur = cur.next; // 这里移动节点的位置移动为啥与pre 没有关系了呢？
        }
        System.out.println(pre.next.val);
    }
}

