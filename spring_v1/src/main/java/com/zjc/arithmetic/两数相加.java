package com.zjc.arithmetic;

/**
 * @ClassName : 两数相加
 * @Author : zhangjiacheng
 * @Date : 2021/6/6
 * @Description : TODO
 */
public class 两数相加 {

     public class ListNode {
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
    int carry = 0;//记录进位
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //定义终止条件，当l1,l2指针都为null时且进位为0 ->null
        if (l1==null && l2==null && carry==0){
            return null;
        }

        //当有一条链表为null 且 进位为0时，next指针直接指向另外一条链表返回
        if(l1!=null&&l2==null&&carry==0){
            return l1;
        }else if(l1==null&&l2!=null&&carry==0){
            return l2;
        }

        //sum = 两链表指针位置上的数字加上进位
        int n1 = l1!=null?l1.val:0;
        int n2 = l2!=null?l2.val:0;
        int sum = n1+n2+carry;
        //计算进位
        carry = sum/10;
        //计算链表的value
        int value = sum % 10;
        ListNode node = new ListNode(value);

        //递归算出这个node的next指向
        node.next = addTwoNumbers((l1!=null?l1.next:null),(l2!=null?l2.next:null));

        return node;

    }
}
