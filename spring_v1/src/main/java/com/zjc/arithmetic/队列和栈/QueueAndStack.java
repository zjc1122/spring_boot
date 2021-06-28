package com.zjc.arithmetic.队列和栈;

/**
 * @ClassName : ReverseList
 * @Author : zhangjiacheng
 * @Date : 2021/5/10
 * @Description : 队列和栈
 */
public class QueueAndStack {

    public static class Node<T>{
        public T val;
        public Node<T> next;
        public Node<T> last;

        public Node(T val) {
            this.val = val;
        }
    }

    public static class DoubleEndsQueue<T>{
        public Node<T> head;
        public Node<T> tail;

        public void addFromHead(T value){
            Node<T> cur = new Node<T>(value);
            if (head == null){
                head = cur;
                tail = cur;
            }else {
                cur.next = head;
                head.last = cur;
                head = cur;
            }
        }

        public void addFromBottom(T value){
            Node<T> cur = new Node<T>(value);
            if (head == null){
                head = cur;
                tail = cur;
            }else {
                cur.last = tail;
                tail.next = cur;
                tail = cur;
            }
        }

        public T popFromHead(){
            if (head == null){
                return null;
            }
            Node<T> cur = head;
            if (head==tail){
                head=null;
                tail = null;
            }else {
                head = head.next;
                cur.next = null;
                head.last = null;
            }
            return cur.val;
        }

        public T popFromBottom(){
            if (head == null){
                return null;
            }
            Node<T> cur = tail;
            if (head==tail){
                head=null;
                tail = null;
            }else {
                tail = tail.last;
                tail.next = null;
                cur.last = null;
            }
            return cur.val;
        }
    }

    public static class MyStack<T>{
        private DoubleEndsQueue<T> queue;
        public MyStack(){
            queue = new DoubleEndsQueue<T>();
        }
        public void push(T val){
            queue.addFromHead(val);
        }
        public T pop(){
            return queue.popFromHead();
        }
    }

    public static class MyQueue<T>{
        private DoubleEndsQueue<T> queue;
        public MyQueue(){
            queue = new DoubleEndsQueue<T>();
        }
        public void push(T val){
            queue.addFromHead(val);
        }
        public T pop(){
            return queue.popFromBottom();
        }
    }

}
