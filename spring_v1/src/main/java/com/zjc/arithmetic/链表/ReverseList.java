package com.zjc.arithmetic.链表;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName : ReverseList
 * @Author : zhangjiacheng
 * @Date : 2021/5/10
 * @Description : 链表反转
 */
public class ReverseList {

    static class Node{
        int val;
        Node next;

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    static class DoubleNode{
        int val;
        DoubleNode next;
        DoubleNode last;

        public DoubleNode(int val, DoubleNode next, DoubleNode last) {
            this.val = val;
            this.next = next;
            this.last = last;
        }
    }
    //单向连表迭代(从头开始)
    public static Node iterate(Node head){
        Node prev = null;
        Node next = null;
        while (head!=null){
            next = head.next;//下一个要操作的节点
            head.next = prev;//当前节点反向指
            prev = head;//头节点的前一个节点前移一位
            head = next;//头节点移动一位
        }
        return prev;
    }


    public static Node iterate2(Node head){
        Node prev = null;
        Node next = null;
        while (head!=null){
            next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    //单向连表递归（从尾开始）
    public static Node recursion(Node head){
        if (head == null||head.next==null){
            return head;
        }
        Node recursion = recursion(head.next);
        head.next.next = head;
        head.next = null;
        return recursion;
    }

    //双向连表
    public static DoubleNode reverseDoubleList(DoubleNode head){
        DoubleNode prev = null;
        DoubleNode next = null;
        while (head!=null){
            next = head.next;//拿到要操作的下一个节点（2）
            head.next = prev;//当前节点反向指1->null
            head.last = next;//把2变成1的尾巴，1向后指2
            prev = head;//头节点的前一个节点变成1
            head = next;//2变成头，指针向后移动一位
        }
        return prev;
    }

    //删除链表中指定的值
    public static Node removeValue(Node head,int num){
        while (head!=null){
            if (head.val != num){
                break;
            }
            head = head.next;
        }
        //head 来到第一个不需要删的位置
        Node prev = head;
        Node cur = head;
        while (cur!=null){
            if (cur.val==num){
                prev.next = cur.next;
            }else {
                prev = cur;
            }
            cur = cur.next;
        }
        return head;
    }

    public static void main(String[] args) {
        Node node5 = new Node(5,null);
        Node node4 = new Node(4,node5);
        Node node3 = new Node(3,node4);
        Node node2 = new Node(2,node3);
        Node node1 = new Node(1,node2);
        Node iterate = iterate(node1);
        System.out.println(iterate);
        int a = 8;
        int b = 8<<1 + 1;
        System.out.println(b);
    }
}
