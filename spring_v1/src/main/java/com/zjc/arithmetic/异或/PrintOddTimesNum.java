package com.zjc.arithmetic.异或;

/**
 * @ClassName : NerLeft
 * @Author : zhangjiacheng
 * @Date : 2021/5/12
 * @Description : 异或算法
 */
public class PrintOddTimesNum {

    //数组中只有一种数出现了基数次
    public static void nearestIndex(int[] arr){
        int eor = 0;
        for (int i = 0; i<arr.length; i++) {
            eor ^= arr[i];
        }
        System.out.println(eor);
    }
    //数组中有2种数出现了基数次
    public static void nearestIndex2(int[] arr){
        int eor = 0;
        for (int i = 0; i<arr.length; i++) {
            eor ^= arr[i];
        }
        //eor = a^b
        //eor !=0
        //eor 必然有一个位置上是1
        int rightOne = eor & (~eor + 1); //取出最右的1（000001000）
        int onlyOne = 0;//eor‘
        for (int i = 0; i<arr.length; i++){
            if ((arr[i]&rightOne)!=0){ //表示数组中的数在某一位有1
                onlyOne ^= arr[i];
            }
        }
        System.out.println(onlyOne + " " + (eor ^ onlyOne));
    }
    //一个数二进制中有多少个1
    public static int bit1counts(int n){
        int count = 0;
        while (n != 0){
            int rightOne = n & ((~n) + 1);
            count ++;
            n ^= rightOne;
        }
        return count;
    }

    public static void main(String[] args) {
        int [] arr = {1,1,2,2,2,3,2};
        int [] arr1 = {1,1,2,2,2,3,2,1};
        nearestIndex(arr);
        nearestIndex2(arr1);
        int i = bit1counts(7);
        //0111
        //0001
        System.out.println(i);
    }
}
