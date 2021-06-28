package com.zjc.arithmetic;

/**
 * @ClassName : HasCycle
 * @Author : zhangjiacheng
 * @Date : 2021/6/3
 * @Description : 判断链表是否有环
 */
public class 链表是否有环和环入口 {

     class ListNode {
          int val;
          ListNode next;
          ListNode(int x) {
              val = x;
              next = null;
          }
     }

     //是否有环
    public boolean hasCycle(ListNode head) {
         if (head==null || head.next==null){
             return false;
         }
         ListNode slow = head;
         ListNode fast = head.next;
         while (slow!=fast){
             if (fast==null || fast.next==null){
                 return false;
             }
             slow = slow.next;
             fast = fast.next.next;
         }
         return true;
    }

    //环的入口
    public ListNode detectCycle(ListNode head) {
        ListNode fast=head;
        ListNode slow = head;
        ListNode st = null;
        while((fast != null) && (fast.next != null)){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow){
                //有环
                //记住相遇点的指针，一个从相遇点开始，一个从头开始，相遇的地方就是环的入口点
                ListNode tmp = head;
                //假设链表的开始就是入口点
                //如果链表的开始是入口点，最后一定在开始的位置相遇
                //因为fast是slow的两倍，当fast走了一圈的时候,slow走半圈，最后在fast走两圈，slow走一圈的位置相遇
                if(tmp == fast){
                    return fast;
                }
                while(true){
                    fast = fast.next;
                    tmp = tmp.next;
                    if(fast == tmp){
                        break;
                    }
                }
                return fast;
            }
        }
        return null;
    }
}
