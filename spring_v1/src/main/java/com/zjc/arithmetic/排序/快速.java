package com.zjc.arithmetic.排序;

/**
 * @ClassName : 快速
 * @Author : zhangjiacheng
 * @Date : 2021/6/15
 * @Description : TODO
 */
public class 快速 {

    public static void sort(int[] arr){
        if (arr==null||arr.length<2){
            return;
        }
        process(arr,0,arr.length-1);
    }
    public static void process(int arr[],int l, int r){
        if (l>=r){
            return;
        }
        swap(arr,l+(int) (Math.random()*(r-l+1)),r);
        int[] area = nether(arr,l,r);
        process(arr,l,area[0]-1);
        process(arr,area[1]+1,r);
    }
    public static int[] nether(int arr[],int l, int r){
        if (l>r){
            return new int[] {-1,-1};
        }
        if (l==r){
            return new int[] {l,r};
        }
        int less = l-1;
        int more = r;
        int index= l;
        while (index < more){
            if (arr[index] == arr[r]){
                index++;
            }else if (arr[index] < arr[r]){
                swap(arr,index++,++less);
            }else {
                swap(arr,index,more--);
            }
        }
        swap(arr,more,r);
        return new int[] {less+1,more};
    }
    private static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i]^arr[j];
        arr[j] = arr[i]^arr[j];
        arr[i] = arr[i]^arr[j];
    }

    public static void main(String[] args) {
        int[] arr ={4,3,2,6,5,1,8,9,7,3};
        sort(arr);
        System.out.println();
    }
}
