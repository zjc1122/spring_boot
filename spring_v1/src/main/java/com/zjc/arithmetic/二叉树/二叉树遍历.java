package com.zjc.arithmetic.二叉树;

import com.zjc.arithmetic.二叉树层序遍历;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName : 二叉树遍历
 * @Author : zhangjiacheng
 * @Date : 2021/6/15
 * @Description : TODO
 */
public class 二叉树遍历 {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    //先序遍历
    public static void pre(TreeNode head){

        if (head==null){
            return;
        }
        System.out.println(head.val);
        pre(head.left);
        pre(head.right);
    }
    //中序遍历
    public static void in(TreeNode head){
        if (head==null){
            return;
        }
        in(head.left);
        System.out.println(head.val);
        in(head.right);
    }
    //先序遍历
    public static void pos(TreeNode head){
        if (head==null){
            return;
        }
        pos(head.left);
        pos(head.right);
        System.out.println(head.val);
    }

    public static void main(String[] args) {
        TreeNode t4 = new TreeNode(4,null,null);
        TreeNode t3 = new TreeNode(3,null,null);
        TreeNode t2 = new TreeNode(2,null,null);
        TreeNode t5 = new TreeNode(5,t2,null);
        TreeNode t6 = new TreeNode(6,t4,t3);
        TreeNode t7 = new TreeNode(7,t6,t5);
        pre(t7);
        in(t7);
        pos(t7);
    }
}
