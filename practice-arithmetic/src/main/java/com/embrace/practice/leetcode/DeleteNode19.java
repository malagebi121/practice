package com.embrace.practice.leetcode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author embrace
 * @describe   删除倒数的第 n 个节点
 *
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 *
 * @date created in 2021/1/15 11:31
 */
public class DeleteNode19 {

    //遍历长度，得到要删除的前一个节点
    public ListNode removeNthFromEnd(ListNode head, int n) {
            //  得到长度
            int length = 1;
            ListNode  cur1 = head;
            while(cur1.next != null){
                length ++;
                cur1 = cur1.next;
            }
            int remove = length - n;
            //如果移除第一个就返回第二个
            if(remove == 0){
                return head.next;
            }
            //找到要删除的前一个元素
            ListNode  cur = head;
            for(int i = 0; i < remove - 1; i++){
                cur = cur.next;
            }
            //这个元素的下一个等于下下两个
            cur.next = cur.next.next;
            return head;
    }


    //双指针遍历，快慢指针 ，暂时没看明白
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        //双指针找倒数第n个节点
        ListNode slow = head,fast = head,pre = head;
        while(n>0){
            fast = fast.next;
            n--;
        }
        while(fast!=null){
            pre = slow;
            slow = slow.next;
            fast = fast.next;
        }
        if(slow==head){
            return head.next;
        }
        pre.next = slow.next;
        return head;
    }

    // 解法3  ， 用栈的方式
    public ListNode removeNthFromEnd3(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        Deque<ListNode> stack = new LinkedList<ListNode>();
        ListNode cur = dummy;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        for (int i = 0; i < n; ++i) {
            stack.pop();
        }
        ListNode prev = stack.peek();
        prev.next = prev.next.next;
        ListNode ans = dummy.next;
        return ans;
    }




}

