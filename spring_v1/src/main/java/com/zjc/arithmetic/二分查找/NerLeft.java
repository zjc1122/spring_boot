package com.zjc.arithmetic.二分查找;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @ClassName : NerLeft
 * @Author : zhangjiacheng
 * @Date : 2021/5/12
 * @Description : 二分查找，有序数组中，找大于等于某一个值最左侧的位置
 */
public class NerLeft {

    public static int nearestIndex(int[] arr, int value){
        int L = 0;
        int R = arr.length - 1;
        int index = -1; //记录最左的对号
        while (L<=R){
            int mid = L + ((R - L) >> 1);
            if (arr[mid]>=value){
                index = mid;
                R = mid - 1;
            }else{
                L = mid +1;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int [] arr = {1,2,3,6,9};
        int i = nearestIndex(arr, 6);
        System.out.println(i);
    }
}
