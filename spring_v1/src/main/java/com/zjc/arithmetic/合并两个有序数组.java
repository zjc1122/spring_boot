package com.zjc.arithmetic;

import java.util.Arrays;

/**
 * @ClassName : 合并两个有序数组
 * @Author : zhangjiacheng
 * @Date : 2021/6/27
 * @Description : TODO
 */
public class 合并两个有序数组 {

    public static void merge(int[] nums1, int m, int[] nums2, int n){
        for (int i=0;i<n;++i){
            nums1[m+i] = nums2[i];
        }
        Arrays.sort(nums1);
    }

    public static void main(String[] args) {
        int[] nums1 = new int[]{1,3,4,5,0,0,0,0};
        int[] nums2 = new int[]{2,3,4,6};
        merge(nums1,4,nums2,4);
    }
}
