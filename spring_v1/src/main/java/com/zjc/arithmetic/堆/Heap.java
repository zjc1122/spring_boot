package com.zjc.arithmetic.堆;

import java.util.HashMap;
import java.util.concurrent.Executors;

/**
 * @ClassName : Heap
 * @Author : zhangjiacheng
 * @Date : 2021/6/1
 * @Description : 大根堆排序
 */
public class Heap {

    public static void heapSort(int[] arr){

        //堆排序 O(N*logN)
        for (int i = 0; i<arr.length;i++){
            heapInsert(arr,i);
        }

        //将数组转换成大根堆，不做排序 O(N)
//        for (int i = arr.length-1;i>=0;i--){
//            heapify(arr,i,arr.length);
//        }
        int heapSize = arr.length;
        swap(arr,0,--heapSize);
        while (heapSize>0){
            heapify(arr,0,heapSize);
            swap(arr,0,--heapSize);
        }
    }

    private static void heapify(int[] arr,int index, int heapSize) {
        int left = index * 2 +1;
        while (left < heapSize){
            //左右两个孩子谁大，把自己下标给largest
            //右-> 1.有右边孩子；2.右边孩子比左边大
            //否则，左
            int largest = left +1<heapSize && arr[left+1]>arr[left]?left+1:left;
            largest = arr[largest]>arr[index]?largest:index;
            if (largest==index){
                break;
            }
            swap(arr,largest,index);
            index = largest;
            left = index * 2 + 1;
        }
    }

    private static void heapInsert(int[] arr, int i) {
        while (arr[i]>arr[(i-1) / 2]){
            swap(arr,i,(i-1) / 2);
            i = (i-1) / 2;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i]^arr[j];
        arr[j] = arr[i]^arr[j];
        arr[i] = arr[i]^arr[j];
    }

    public static void main(String[] args) {
        int[] i = {3,5,9,5,6,1,7};
        heapSort(i);
    }
}
