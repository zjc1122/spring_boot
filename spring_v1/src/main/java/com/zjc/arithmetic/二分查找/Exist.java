package com.zjc.arithmetic.二分查找;

/**
 * @ClassName : NerLeft
 * @Author : zhangjiacheng
 * @Date : 2021/5/12
 * @Description : 二分查找，有序数组中，判断某一个数是否存在
 */
public class Exist {

    public static boolean exist(int[] arr, int value){
        if (arr==null ||arr.length==0){
            return false;
        }
        int L = 0;
        int R = arr.length - 1;
        int mid = 0;
        while (L<R){
            mid = L + ((R - L) >> 1); // mid = (L + R)/2
            if (arr[mid]==value){
                return true;
            }else if (arr[mid]>value){
                R = mid - 1;
            }else{
                L = mid + 1;
            }
        }
        return arr[L] == value;
    }

    public static void main(String[] args) {
        int [] arr = {1,3,6,7};
        boolean i = exist(arr, 4);
        System.out.println(i);
    }
}
