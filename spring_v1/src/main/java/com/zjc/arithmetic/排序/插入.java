package com.zjc.arithmetic.排序;

/**
 * @ClassName : InsertionSort
 * @Author : zhangjiacheng
 * @Date : 2021/5/11
 * @Description : 插入排序
 */
public class 插入 {

    public static void insertionSort(int[] arr){
        if (arr==null || arr.length<2){
            return;
        }
        //0~0有序的
        //0~i想有序
        for (int i = 1;i<arr.length;i++){//0~i做到有序
            //arr[i]往前看，一直交换到合适的位置停止
            for (int j = i -1;j>=0&&arr[j]>arr[j+1];j--){
                swap(arr,j,j+1);
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i]^arr[j];
        arr[j] = arr[i]^arr[j];
        arr[i] = arr[i]^arr[j];
    }

    //生成一个随机数组
    public static int[] generateRandomArray(int maxSize, int maxValue){
        //Math.random() [0,1)
        //Math.random() * N [0,N)
        //(int) (Math.random() * N) [0,N-1)
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i<arr.length;i++){
            arr[i] = (int) ((maxValue+1) * Math.random()) -  (int) ((maxValue) * Math.random());
        }
        return arr;
    }

    public static void main(String[] args) {
        int [] arr = {5,3,6,1,7};
        insertionSort(arr);
        System.out.println(1);
    }
}
