package com.zjc.arithmetic.二分查找;

import java.util.LinkedHashMap;
import java.util.Stack;

/**
 * @ClassName : NerLeft
 * @Author : zhangjiacheng
 * @Date : 2021/5/12
 * @Description : 二分查找，有序数组中，找小于等于某一个值最右侧的位置
 */
public class NerRight {

    public static int nearestIndex(int[] arr, int value){
        int L = 0;
        int R = arr.length - 1;
        int index = -1; //记录最右的对号
        while (L<=R){
            int mid = L + ((R - L) >> 1);
            if (arr[mid]<=value){
                index = mid;
                L = mid + 1;
            }else{
                R = mid - 1;
            }
        }
        return index;
    }

    //X值开方
    public static int mySqrt(int x){

        if(x == 1){
            return 1;
        }
        int min = 0;
        int max = x;
        while(max-min>1)
        {
            int m = (max+min)/2;
            if(x/m<m) {
                max = m;
            }
            else {
                min = m;
            }
        }
        return min;
    }


    public static void main(String[] args) {
        int [] arr = {1,2,3,3,3,4,5,6,6,6,6,7};
        int i = nearestIndex(arr, 6);
        System.out.println(i);

        int i1 = mySqrt(9);
        System.out.println(i1);
    }
}
