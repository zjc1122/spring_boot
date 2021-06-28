package com.zjc.arithmetic;

import java.util.PriorityQueue;

/**
 * @ClassName : 数组中的第K个最大元素
 * @Author : zhangjiacheng
 * @Date : 2021/6/13
 * @Description : TODO
 */
public class 数组中的第K个最大元素 {


    public static int test3(int[] nums,int k){

        PriorityQueue<Integer> q = new PriorityQueue<>(nums.length, (o1, o2) -> o2-o1);
        for (int i : nums) {
            q.offer(i);
        }
        for (int j = 1;j<=k;j++){
            Integer poll = q.poll();
            if (j==k){
                return poll;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {7,4,2,5,6,1,3};
        int test3 = test3(arr,3);
        System.out.println(test3);
    }
}
