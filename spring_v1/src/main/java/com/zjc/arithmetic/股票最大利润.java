package com.zjc.arithmetic;

/**
 * @ClassName : 股票最大利润
 * @Author : zhangjiacheng
 * @Date : 2021/6/23
 * @Description : TODO
 */
public class 股票最大利润 {

    public static int maxProfit(int prices[]) {

        int minprice = Integer.MAX_VALUE;
        int maxprofit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minprice) {
                minprice = prices[i];
            } else if (prices[i] - minprice > maxprofit) {
                maxprofit = prices[i] - minprice;
            }
        }
        return maxprofit;
    }


    public static void main(String[] args) {
        int[] arr = new int[]{7,1,5,3,6,4};
        int i = maxProfit(arr);
        System.out.println(i);
    }
}
