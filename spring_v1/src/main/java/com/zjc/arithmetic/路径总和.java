package com.zjc.arithmetic;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @ClassName : 路径总和
 * @Author : zhangjiacheng
 * @Date : 2021/6/21
 * @Description : TODO
 */
public class 路径总和 {

    public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }
    static List<List<Integer>> list = new LinkedList<List<Integer>>();
    static Deque<Integer> path = new LinkedList<Integer>();
    public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        test(root,targetSum);

        return list;
    }

    private static void test(TreeNode root, int targetSum){
        if (root==null){
            return;
        }
        path.offerLast(root.val);
        targetSum -= root.val;
        if (root.left == null && root.right == null && targetSum == 0) {
            list.add(new LinkedList<Integer>(path));
        }
        test(root.left, targetSum);
        test(root.right, targetSum);
        path.pollLast();
    }

    public static void main(String[] args) {
        TreeNode node5 = new TreeNode(5,null,null);
        TreeNode node1 = new TreeNode(1,null,null);
        TreeNode node7 = new TreeNode(7,null,null);
        TreeNode node2 = new TreeNode(2,null,null);
        TreeNode node11 = new TreeNode(11,node7,node2);
        TreeNode node13 = new TreeNode(13,null,null);
        TreeNode node4 = new TreeNode(4,node5,node1);
        TreeNode node8 = new TreeNode(8,node13,node4);
        TreeNode node4_1 = new TreeNode(4,node11,null);
        TreeNode node5_1 = new TreeNode(5,node4_1,node8);
        List<List<Integer>> lists = pathSum(node5_1, 22);
        System.out.println(lists);
    }
}
