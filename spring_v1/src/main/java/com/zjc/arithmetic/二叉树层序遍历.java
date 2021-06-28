package com.zjc.arithmetic;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @ClassName : 二叉树层序遍历
 * @Author : zhangjiacheng
 * @Date : 2021/6/9
 * @Description : TODO
 */
public class 二叉树层序遍历 {

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

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        if (root == null){
            return list;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            List<Integer> level = new ArrayList<Integer>();
            int currentLevelSize = queue.size();
            for (int i = 1; i<=currentLevelSize;++i){
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left!=null){
                    queue.offer(node.left);
                }
                if (node.right!=null){
                    queue.offer(node.right);
                }
            }
            list.add(level);
        }
        return list;
    }

    public static List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        if (root==null){
            return list;
        }
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);
        while (!q.isEmpty()){
            List<Integer> l = new ArrayList<Integer>();
            int size = q.size();
            for (int i = 1;i<=size;++i){
                TreeNode poll = q.poll();
                l.add(poll.val);
                if (poll.left!=null){
                    q.offer(poll.left);
                }
                if (poll.right!=null){
                    q.offer(poll.right);
                }
            }
            list.add(l);
        }
        return list;
    }

    public static List<List<Integer>> levelOrder3(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        if (root==null){
            return list;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()){
            List<Integer> objects = new ArrayList<>();
            for (int i=1;i<=q.size();++i){
                TreeNode poll = q.poll();
                objects.add(poll.val);
                if (poll.left!=null){
                    q.offer(poll.left);
                }
                if (poll.right!=null){
                    q.offer(poll.right);
                }
            }
            list.add(objects);
        }
        return list;
    }

    public static void main(String[] args) {
        TreeNode node7 = new TreeNode(7,null,null);
        TreeNode node15 = new TreeNode(15,null,null);
        TreeNode node20 = new TreeNode(20,node15,node7);
        TreeNode node9 = new TreeNode(9,null,null);
        TreeNode node3 = new TreeNode(3,node9,node20);
        List<List<Integer>> lists = levelOrder2(node3);
        System.out.println(lists);
    }
}
