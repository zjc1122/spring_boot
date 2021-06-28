package com.zjc.arithmetic;

/**
 * @ClassName : 整数反转
 * @Author : zhangjiacheng
 * @Date : 2021/6/6
 * @Description : TODO
 */
public class 整数反转 {

    public static int reverse(int x) {
        int rev = 0;
        while (x!=0){
            if (rev>Integer.MAX_VALUE /10 || rev<Integer.MIN_VALUE / 10){
                return 0;
            }
            int d = x%10;
            x/=10;
            rev = rev* 10 + d;
        }
        return rev;
    }

    public static void main(String[] args) {
        int reverse = reverse(123);
        System.out.println(reverse);
    }

}
