package com.zjc.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @ClassName : 两个有序数组的重复项
 * @Author : zhangjiacheng
 * @Date : 2021/6/28
 * @Description : TODO
 */
public class 两个有序数组的重复项 {

    public static List<Integer> findCommon(int[] nums1, int[] nums2){
        List<Integer> list = new ArrayList<>();
        if (nums1==null||nums2==null){
            return list;
        }
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i=0,j=0;
        while (i<nums1.length&&j<nums2.length){
            if (nums1[i]==nums2[j]){
                list.add(nums1[i]);
                i++;
                j++;
            }else if (nums1[i]<nums2[j]){
                i++;
            }else {
                j++;
            }
        }
        return list;
    }

    public static void main(String[] args) {
        int[] nums1 = new int[]{1,3,4,5,9};
        int[] nums2 = new int[]{2,3,4,6,9};
        List<Integer> common = findCommon(nums1, nums2);
        common.forEach(System.out::println);
    }
}
