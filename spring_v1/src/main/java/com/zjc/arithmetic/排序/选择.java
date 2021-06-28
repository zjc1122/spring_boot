package com.zjc.arithmetic.排序;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName : SelectionSort
 * @Author : zhangjiacheng
 * @Date : 2021/5/11
 * @Description : 选择排序
 */
public class 选择 {

    public static void selectionSort(int[] arr){

        if (arr==null || arr.length<2){
            return;
        }
        //0~n-1
        //1~n-1
        //2~n-1
        for (int i = 0;i<arr.length - 1;i++){
            //最小值在哪个位置上 i~n-1
            int minIndex = i;
            for (int j = i+1;j<arr.length;j++){//i~n-1上找最小值的下标
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;
            }
            swap(arr, i, minIndex);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tem = arr[i];
        arr[i] = arr[j];
        arr[j] = tem;
    }

    public static void main(String[] args) throws ParseException {
        int [] arr = {5,3,6,1,7};
        selectionSort(arr);
        System.out.println(1);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = df.parse("2021-06-01");
        System.out.println(parse);
        String ss = "202105";
        String substring = ss.substring(0, 4);
        String substring2 = ss.substring(4, 6);
        String s = ss.substring(0, 4) + "-" + ss.substring(4, 6) + "-01";
        System.out.println(substring);
        System.out.println(substring2);
        System.out.println(s);
    }
}
