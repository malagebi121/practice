package com.embrace.practice.leetcode;


/**
 * @author embrace
 * @describe
 *
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 *
 * @date created in 2021/1/15 13:18
 */
public class NodeReverse24 {
    public ListNode reverseList(ListNode head) {
        //后来的一个
        ListNode prev = null;
        //移动指针
        ListNode cur = head;
        while (cur != null){
            ListNode node = cur.next;
            cur.next = prev;
            prev = cur;
            cur = node;
        }
        return prev;
    }
}
