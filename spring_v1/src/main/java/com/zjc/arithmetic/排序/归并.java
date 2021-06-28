package com.zjc.arithmetic.排序;

/**
 * @ClassName : 归并
 * @Author : zhangjiacheng
 * @Date : 2021/6/14
 * @Description : TODO
 */
public class 归并 {

    public static void mergeSort(int[] arr){
        if (arr==null||arr.length<2){
            return;
        }
        process(arr,0,arr.length-1);
    }

    private static void process(int[] arr, int l, int r) {
        if (l==r){
            return;
        }

        int mid = l + ((r - l) >> 1);
        process(arr,l,mid);
        process(arr,mid+1,r);
        merge(arr,l,mid,r);
    }

    private static void merge(int[] arr, int l, int m, int r) {
        int[] help = new int[r-l+1];
        int i = 0;
        int p1 = l;
        int p2 = m+1;
        //p1和p2都不越界
        while (p1 <= m && p2 <= r){
            help[i++] = arr[p1]<arr[p2]?arr[p1++]:arr[p2++];
        }
        while (p1<=m){
            help[i++] = arr[p1++];
        }
        while (p2<=r){
            help[i++] = arr[p2++];
        }
        for (i=0;i<help.length;i++){
            arr[l+i]=help[i];
        }
    }

    public static void main(String[] args) {
        int[] arr ={4,3,2,6,5,1,8,9,7};
        mergeSort(arr);
        System.out.println();
    }
}
