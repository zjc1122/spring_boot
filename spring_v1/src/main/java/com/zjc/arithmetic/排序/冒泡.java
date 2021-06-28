package com.zjc.arithmetic.排序;

/**
 * @ClassName : BubbleSort
 * @Author : zhangjiacheng
 * @Date : 2021/5/11
 * @Description : 冒泡排序
 */
public class 冒泡 {

    public static void bubbleSort(int[] arr){
        if (arr==null||arr.length<2){
            return;
        }

        for (int e = arr.length - 1;e>0;e--){
            for (int i = 0; i<e;i++){
                if (arr[i]>arr[i+1]){
                    swap(arr,i,i+1);
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i]^arr[j];
        arr[j] = arr[i]^arr[j];
        arr[i] = arr[i]^arr[j];
    }

    public static void main(String[] args) {
        int [] arr = {5,3,6,1,7};
        bubbleSort(arr);
        System.out.println(1);
    }
}
