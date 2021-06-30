package com.zjc.arithmetic;

public class 合并两个有序链表 {


    public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        if (l1==null){
            return l2;
        }else if (l2==null){
            return l1;
        }else if(l1.val<l2.val){
            l1.next = mergeTwoLists(l1.next,l2);
            return l1;
        }else{
            l2.next = mergeTwoLists(l1,l2.next);
            return l2;
        }
    }

    public static void main(String[] args) {

        ListNode l4 = new ListNode(4,null);
        ListNode l3 = new ListNode(3,l4);
        ListNode l2 = new ListNode(2,l3);
        ListNode l1 = new ListNode(1,l2);
        ListNode l41 = new ListNode(4,null);
        ListNode l31 = new ListNode(3,l41);
        ListNode l21 = new ListNode(2,l31);
        ListNode listNode = mergeTwoLists(l1, l21);
        System.out.println(listNode);
    }
}
