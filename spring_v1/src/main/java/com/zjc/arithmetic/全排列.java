package com.zjc.arithmetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName : 全排列
 * @Author : zhangjiacheng
 * @Date : 2021/6/24
 * @Description : TODO
 */
public class 全排列 {

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> output = new ArrayList<Integer>();
        for (int num : nums) {
            output.add(num);
        }

        int n = nums.length;
        backtrack(n, output, list, 0);
        return list;
    }

    public static void backtrack(int n, List<Integer> output, List<List<Integer>> list, int f) {
        if (n==f){
            list.add(new ArrayList<Integer>(output));
        }
        for (int i = f;i<n;i++){
            Collections.swap(output,f,i);
            backtrack(n,output,list,f+1);
            Collections.swap(output,f,i);
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3};
        List<List<Integer>> permute = permute(arr);
        System.out.println(permute);
    }
}
