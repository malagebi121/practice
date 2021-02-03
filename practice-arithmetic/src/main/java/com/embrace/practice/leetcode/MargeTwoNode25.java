package com.embrace.practice.leetcode;

/**
 * @author embrace
 * @describe   合并两个链表
 * 输入两个递增排序的链表，合并这两个链表并使新链表中的节点仍然是递增排序的。
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 *
 * 用 ||  超出时间限制
 *
 * @date created in 2021/1/15 12:50
 */
public class MargeTwoNode25 {

    // 这样做直接超出时间限制
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode node = new ListNode(0);
        ListNode cur = node;
        while (l1 != null || l2 !=null){
            int m = l1 == null ? Integer.MAX_VALUE : l1.val;
            int n = l2 == null ? Integer.MAX_VALUE : l2.val;
            if(m < n){
                cur = l1;
                if(l1.next != null){
                    l1 = l1.next;
                }
            }else{
                cur = l2;
                if(l2.next != null){
                    l2= l2.next;
                }
            }
            cur = cur.next;
        }
        return  node.next;
    }


    //  用最后的剩余的列表直接赋值
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        ListNode node = new ListNode(0);
        ListNode cur = node;
        while (l1 != null && l2 !=null){
            if(l1.val < l2.val){
                cur.next = l1;
                l1 = l1.next;
            }else{
                cur.next = l2;
                l2= l2.next;
            }
            cur = cur.next;
        }
        cur.next = l1 != null ? l1 : l2;
        return  node.next;
    }
}
