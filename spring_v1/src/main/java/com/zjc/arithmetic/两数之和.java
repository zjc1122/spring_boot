package com.zjc.arithmetic;

/**
 * @ClassName : 两数之和
 * @Author : zhangjiacheng
 * @Date : 2021/6/6
 * @Description : TODO
 */
public class 两数之和 {

    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        for(int i = 0 ;i<n;i++){
            for(int j = i+1;j<n;j++){
                if(nums[i]+nums[j]==target){
                    return new int[]{i,j};
                }
            }
        }
        return new int[0];
    }
}
