package com.zjc.arithmetic;

import com.zjc.arithmetic.堆.Heap;

/**
 * @ClassName : 删除链表的倒数第N个结点
 * @Author : zhangjiacheng
 * @Date : 2021/6/7
 * @Description : TODO
 */
public class 删除链表的倒数第N个结点 {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode d = new ListNode(0,head);
        int length = getLength(head);
        ListNode cur = d;
        for (int i=1; i<length-n+1;i++){
            cur = cur.next;
        }
        cur.next = cur.next.next;
        ListNode re = d.next;
        return re;
    }

    private static int getLength(ListNode head){

        int i = 0;
        while (head!=null){
            ++i;
            head = head.next;
        }
        return i;
    }

    public static void main(String[] args) {
        ListNode node5 = new ListNode(5,null);
        ListNode node4 = new ListNode(4,node5);
        ListNode node3 = new ListNode(3,node4);
        ListNode node2 = new ListNode(2,node3);
        ListNode node1 = new ListNode(1,node2);
        ListNode node = removeNthFromEnd(node1, 2);

    }
}
