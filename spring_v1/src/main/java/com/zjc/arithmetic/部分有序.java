package com.zjc.arithmetic;

import java.util.Arrays;

/**
 * @ClassName : 部分有序
 * @Author : zhangjiacheng
 * @Date : 2021/6/22
 * @Description : TODO
 */
public class 部分有序 {

    public static int[] sub(int[] array) {
        if(array == null || array.length == 0){
            return new int[]{-1, -1};
        }
        int last = -1, first = -1;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        int len = array.length;
        for(int i = 0; i < len; i++){
            if(array[i] < max){
                last = i;
            }else{
                max = Math.max(max, array[i]);
            }

            if(array[len - 1 - i] > min){
                first = len - 1 - i;
            }else{
                min = Math.min(min, array[len - 1 - i]);
            }
        }
        return new int[] {first, last};
    }

    public static void main(String[] args) {
        int[] arrs = {1,2,4,7,10,11,7,12,6,7,16,18,19};
        final int[] sub = sub(arrs);
        System.out.println(Arrays.toString(sub));
    }
}
